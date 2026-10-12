package com.dukankhata.supplier;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.common.api.ApiResponse; import com.dukankhata.supplier.dto.*; import jakarta.validation.Valid; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.util.*;
@AuditResource("SUPPLIER")
@RestController @RequestMapping("/api/v1/suppliers") public class SupplierController{
 private final SupplierService service; private final com.dukankhata.idempotency.IdempotencyService idempotency; public SupplierController(SupplierService service, com.dukankhata.idempotency.IdempotencyService idempotency){this.service=service;this.idempotency=idempotency;}
 @GetMapping public ApiResponse<List<SupplierResponse>> list(){return ApiResponse.ok(service.list());}
 @PostMapping public ApiResponse<SupplierResponse> create(@Valid @RequestBody SupplierRequest r){return ApiResponse.ok(service.create(r));}
 @PutMapping("/{id}") public ApiResponse<SupplierResponse> update(@PathVariable UUID id,@Valid @RequestBody SupplierRequest r){return ApiResponse.ok(service.update(id,r));}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable UUID id){service.deactivate(id);return ResponseEntity.noContent().build();}
 @GetMapping("/{id}/ledger") public ApiResponse<List<SupplierLedgerResponse>> ledger(@PathVariable UUID id){return ApiResponse.ok(service.ledger(id));}
 @PostMapping("/{id}/payments") public ApiResponse<SupplierResponse> pay(@PathVariable UUID id,@RequestHeader("Idempotency-Key") String key,@Valid @RequestBody SupplierPaymentRequest r){return ApiResponse.ok(idempotency.execute("SUPPLIER_PAYMENT:"+id,key,r,SupplierResponse.class,()->service.pay(id,r)));}
}
