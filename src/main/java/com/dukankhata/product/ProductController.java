package com.dukankhata.product;
import com.dukankhata.common.audit.AuditResource;

import com.dukankhata.common.api.ApiResponse;
import com.dukankhata.product.dto.ProductRequest;
import com.dukankhata.product.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AuditResource("PRODUCT")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<ProductResponse>> list() {
        return ApiResponse.ok(service.list());
    }

    @PreAuthorize("hasRole('OWNER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return ApiResponse.ok(service.create(request));
    }

    @PreAuthorize("hasRole('OWNER')")
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
        return ApiResponse.ok(service.update(id, request));
    }

    @PreAuthorize("hasRole('OWNER')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deactivate(@PathVariable UUID id) {
        service.deactivate(id);
        return ApiResponse.success("Product deactivated successfully");
    }
}
