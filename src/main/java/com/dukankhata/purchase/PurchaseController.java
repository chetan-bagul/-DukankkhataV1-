package com.dukankhata.purchase;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.common.api.ApiResponse; import com.dukankhata.purchase.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*; import java.util.*;
@AuditResource("PURCHASE")
@PreAuthorize("hasRole('OWNER')")
@RestController @RequestMapping("/api/v1/purchases") public class PurchaseController{
 private final PurchaseService service; private final com.dukankhata.idempotency.IdempotencyService idempotency; public PurchaseController(PurchaseService service, com.dukankhata.idempotency.IdempotencyService idempotency){this.service=service;this.idempotency=idempotency;}
 @GetMapping public ApiResponse<List<PurchaseResponse>> list(){return ApiResponse.ok(service.list());}
 @GetMapping("/{id}") public ApiResponse<PurchaseResponse> get(@PathVariable UUID id){return ApiResponse.ok(service.get(id));}
 @PostMapping public ApiResponse<PurchaseResponse> create(@RequestHeader("Idempotency-Key") String key,@Valid @RequestBody CreatePurchaseRequest r){return ApiResponse.ok(idempotency.execute("PURCHASE_CREATE",key,r,PurchaseResponse.class,()->service.create(r)));}
}
