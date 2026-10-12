package com.dukankhata.product;

import com.dukankhata.common.cache.CacheLeaseService;
import com.dukankhata.common.exception.ConflictException;
import com.dukankhata.common.exception.ResourceNotFoundException;
import com.dukankhata.common.security.CurrentUserService;
import com.dukankhata.product.dto.ProductRequest;
import com.dukankhata.product.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final CurrentUserService currentUser;
    private final ProductCacheService cache;
    private final CacheLeaseService leaseService;

    public ProductService(ProductRepository repository,
                          CurrentUserService currentUser,
                          ProductCacheService cache,
                          CacheLeaseService leaseService) {
        this.repository = repository;
        this.currentUser = currentUser;
        this.cache = cache;
        this.leaseService = leaseService;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> list() {
        UUID shopId = currentUser.shopId();
        var cached = cache.get(shopId);
        if (cached.isPresent()) return cached.get();

        String leaseKey = "dk:lease:products:" + shopId;
        CacheLeaseService.Lease lease = leaseService.tryAcquire(leaseKey, Duration.ofSeconds(5));
        if (lease != null) {
            try {
                // Double-check after acquiring the lease: another request may have populated the cache first.
                cached = cache.get(shopId);
                if (cached.isPresent()) return cached.get();

                List<ProductResponse> products = repository
                        .findAllByShopIdAndActiveTrueOrderByNameAsc(shopId)
                        .stream().map(ProductResponse::from).toList();
                cache.put(shopId, products);
                return products;
            } finally {
                leaseService.release(lease);
            }
        }

        // Another node is rebuilding the cache. Give it a short chance to populate Redis.
        for (int i = 0; i < 5; i++) {
            try { Thread.sleep(50); } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
            cached = cache.get(shopId);
            if (cached.isPresent()) return cached.get();
        }

        // Safe fallback: correctness is more important than waiting indefinitely for a cache lease.
        return repository.findAllByShopIdAndActiveTrueOrderByNameAsc(shopId)
                .stream().map(ProductResponse::from).toList();
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        UUID shopId = currentUser.shopId();
        String name = request.name().trim();
        if (repository.existsByShopIdAndNameIgnoreCaseAndActiveTrue(shopId, name)) {
            throw new ConflictException("A product with this name already exists");
        }

        Product product = new Product();
        product.setShop(currentUser.requireUser().getShop());
        product.setName(name);
        product.setSellingPrice(request.sellingPrice());
        if(request.averageCostPrice()!=null) product.setAverageCostPrice(request.averageCostPrice());
        product.setHsnCode(blankToNull(request.hsnCode()));
        product.setGstRate(request.gstRate()==null?java.math.BigDecimal.ZERO:request.gstRate());
        product.setReorderLevel(request.reorderLevel()==null?java.math.BigDecimal.ZERO:request.reorderLevel());
        ProductResponse response = ProductResponse.from(repository.save(product));
        cache.evict(shopId);
        return response;
    }

    @Transactional
    public ProductResponse update(UUID id, ProductRequest request) {
        UUID shopId = currentUser.shopId();
        Product product = repository.findByIdAndShopId(id, shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        String name = request.name().trim();
        if (repository.existsByShopIdAndNameIgnoreCaseAndActiveTrueAndIdNot(shopId, name, id)) {
            throw new ConflictException("A product with this name already exists");
        }

        product.setName(name);
        product.setSellingPrice(request.sellingPrice());
        if(request.averageCostPrice()!=null) product.setAverageCostPrice(request.averageCostPrice());
        product.setHsnCode(blankToNull(request.hsnCode()));
        product.setGstRate(request.gstRate()==null?java.math.BigDecimal.ZERO:request.gstRate());
        product.setReorderLevel(request.reorderLevel()==null?java.math.BigDecimal.ZERO:request.reorderLevel());
        ProductResponse response = ProductResponse.from(repository.save(product));
        cache.evict(shopId);
        return response;
    }

    @Transactional
    private String blankToNull(String value) { return value == null || value.isBlank() ? null : value.trim(); }

    public void deactivate(UUID id) {
        UUID shopId = currentUser.shopId();
        Product product = repository.findByIdAndShopId(id, shopId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setActive(false);
        repository.save(product);
        cache.evict(shopId);
    }
}
