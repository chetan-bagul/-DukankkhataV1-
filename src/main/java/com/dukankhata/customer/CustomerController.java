package com.dukankhata.customer;
import com.dukankhata.common.audit.AuditResource;

import com.dukankhata.common.api.ApiResponse;
import com.dukankhata.customer.dto.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@AuditResource("CUSTOMER")
@RestController @RequestMapping("/api/v1/customers")
public class CustomerController {
 private final CustomerService service; private final com.dukankhata.idempotency.IdempotencyService idempotency; public CustomerController(CustomerService service, com.dukankhata.idempotency.IdempotencyService idempotency){this.service=service;this.idempotency=idempotency;}
 @GetMapping public ResponseEntity<ApiResponse<List<CustomerResponse>>> list(){return ResponseEntity.ok(ApiResponse.ok(service.list()));}
 @GetMapping("/{id}") public ResponseEntity<ApiResponse<CustomerResponse>> get(@PathVariable UUID id){return ResponseEntity.ok(ApiResponse.ok(service.get(id)));}
 @PreAuthorize("hasRole('OWNER')") @PostMapping public ResponseEntity<ApiResponse<CustomerResponse>> create(@Valid @RequestBody CustomerRequest request){return ResponseEntity.ok(ApiResponse.ok(service.create(request)));}
 @PreAuthorize("hasRole('OWNER')") @PutMapping("/{id}") public ResponseEntity<ApiResponse<CustomerResponse>> update(@PathVariable UUID id,@Valid @RequestBody CustomerRequest request){return ResponseEntity.ok(ApiResponse.ok(service.update(id,request)));}
 @PreAuthorize("hasRole('OWNER')") @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> deactivate(@PathVariable UUID id){service.deactivate(id);return ResponseEntity.ok(ApiResponse.success("Customer deactivated"));}
 @GetMapping("/{id}/ledger") public ResponseEntity<ApiResponse<List<LedgerEntryResponse>>> ledger(@PathVariable UUID id){return ResponseEntity.ok(ApiResponse.ok(service.ledger(id)));}
 @PostMapping("/{id}/ledger/charge") public ResponseEntity<ApiResponse<LedgerEntryResponse>> charge(@PathVariable UUID id,@RequestHeader("Idempotency-Key") String key,@Valid @RequestBody LedgerEntryRequest request){LedgerEntryResponse result=idempotency.execute("CUSTOMER_CHARGE:"+id,key,request,LedgerEntryResponse.class,()->service.charge(id,request));return ResponseEntity.ok(ApiResponse.ok(result));}
 @PostMapping("/{id}/ledger/payment") public ResponseEntity<ApiResponse<LedgerEntryResponse>> payment(@PathVariable UUID id,@RequestHeader("Idempotency-Key") String key,@Valid @RequestBody LedgerEntryRequest request){LedgerEntryResponse result=idempotency.execute("CUSTOMER_PAYMENT:"+id,key,request,LedgerEntryResponse.class,()->service.payment(id,request));return ResponseEntity.ok(ApiResponse.ok(result));}
}
