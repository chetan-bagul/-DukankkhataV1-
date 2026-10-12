package com.dukankhata.shop;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.common.api.ApiResponse; import com.dukankhata.common.exception.ResourceNotFoundException; import com.dukankhata.common.security.CurrentUserService; import com.dukankhata.shop.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@AuditResource("SHOP")
@PreAuthorize("hasRole('OWNER')")
@RestController @RequestMapping("/api/v1/shop")
public class ShopController { private final ShopRepository shops; private final CurrentUserService current; public ShopController(ShopRepository shops,CurrentUserService current){this.shops=shops;this.current=current;}
 @GetMapping("/settings") public ApiResponse<ShopSettingsResponse> get(){return ApiResponse.success(ShopSettingsResponse.from(shops.findById(current.shopId()).orElseThrow(()->new ResourceNotFoundException("Shop not found"))));}
 @PutMapping("/settings") public ApiResponse<ShopSettingsResponse> update(@Valid @RequestBody ShopSettingsRequest r){Shop s=shops.findById(current.shopId()).orElseThrow(()->new ResourceNotFoundException("Shop not found"));s.setName(r.name().trim());s.setGstin(blank(r.gstin()));s.setLegalName(blank(r.legalName()));s.setAddressLine(blank(r.addressLine()));s.setCity(blank(r.city()));s.setState(blank(r.state()));s.setStateCode(blank(r.stateCode()));s.setPincode(blank(r.pincode()));return ApiResponse.success(ShopSettingsResponse.from(shops.save(s)));}
 private String blank(String x){return x==null||x.isBlank()?null:x.trim().toUpperCase();}
}
