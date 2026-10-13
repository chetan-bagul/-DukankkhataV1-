package com.dukankhata.shop;

import com.dukankhata.common.api.ApiResponse;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.common.cache.CacheService;
import com.dukankhata.common.exception.ResourceNotFoundException;
import com.dukankhata.common.security.CurrentUserService;
import com.dukankhata.shop.dto.ShopSettingsRequest;
import com.dukankhata.shop.dto.ShopSettingsResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.UUID;

@AuditResource("SHOP")
@PreAuthorize("hasRole('OWNER')")
@RestController
@RequestMapping("/api/v1/shop")
public class ShopController {
    private static final Duration SETTINGS_CACHE_TTL = Duration.ofMinutes(5);

    private final ShopRepository shops;
    private final CurrentUserService current;
    private final CacheService cache;

    public ShopController(ShopRepository shops, CurrentUserService current, CacheService cache) {
        this.shops = shops;
        this.current = current;
        this.cache = cache;
    }

    @GetMapping("/settings")
    public ApiResponse<ShopSettingsResponse> get() {
        UUID shopId = current.shopId();
        String key = settingsKey(shopId);
        var cached = cache.get(key, ShopSettingsResponse.class);
        if (cached.isPresent()) return ApiResponse.success(cached.get());

        ShopSettingsResponse response = ShopSettingsResponse.from(shops.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found")));
        cache.put(key, response, SETTINGS_CACHE_TTL);
        return ApiResponse.success(response);
    }

    @PutMapping("/settings")
    public ApiResponse<ShopSettingsResponse> update(@Valid @RequestBody ShopSettingsRequest request) {
        UUID shopId = current.shopId();
        Shop shop = shops.findById(shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop not found"));
        shop.setName(request.name().trim());
        shop.setGstin(blank(request.gstin()));
        shop.setLegalName(blank(request.legalName()));
        shop.setAddressLine(blank(request.addressLine()));
        shop.setCity(blank(request.city()));
        shop.setState(blank(request.state()));
        shop.setStateCode(blank(request.stateCode()));
        shop.setPincode(blank(request.pincode()));

        ShopSettingsResponse response = ShopSettingsResponse.from(shops.save(shop));
        cache.put(settingsKey(shopId), response, SETTINGS_CACHE_TTL);
        return ApiResponse.success(response);
    }

    private String settingsKey(UUID shopId) {
        return "dk:cache:shop-settings:" + shopId;
    }

    private String blank(String value) {
        return value == null || value.isBlank() ? null : value.trim().toUpperCase();
    }
}
