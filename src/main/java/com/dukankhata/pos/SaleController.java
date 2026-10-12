package com.dukankhata.pos;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.common.api.ApiResponse; import com.dukankhata.pos.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.UUID; import java.util.List;
@AuditResource("POS")
@PreAuthorize("hasAnyRole('OWNER','STAFF')")
@RestController @RequestMapping("/api/v1/pos/sales") public class SaleController { private final SaleService service; private final com.dukankhata.idempotency.IdempotencyService idempotency; public SaleController(SaleService service, com.dukankhata.idempotency.IdempotencyService idempotency){this.service=service;this.idempotency=idempotency;}
 @PostMapping public ResponseEntity<ApiResponse<SaleResponse>> create(@RequestHeader("Idempotency-Key") String key,@Valid @RequestBody CreateSaleRequest request){SaleResponse result=idempotency.execute("POS_SALE_CREATE",key,request,SaleResponse.class,()->service.create(request));return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));}
 @GetMapping("/{id}") public ResponseEntity<ApiResponse<SaleResponse>> get(@PathVariable UUID id){return ResponseEntity.ok(ApiResponse.success(service.get(id)));}
 @GetMapping public ResponseEntity<ApiResponse<List<SaleResponse>>> list(){return ResponseEntity.ok(ApiResponse.success(service.list()));}
}
