package com.dukankhata.pos;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.common.api.ApiResponse; import com.dukankhata.pos.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.UUID; import java.util.List;
@AuditResource("POS")
@PreAuthorize("hasAnyRole('OWNER','STAFF')")
@RestController @RequestMapping("/api/v1/pos/sales") public class SaleController { private final SaleService service; private final com.dukankhata.idempotency.IdempotencyService idempotency; public SaleController(SaleService service, com.dukankhata.idempotency.IdempotencyService idempotency){this.service=service;this.idempotency=idempotency;}
 @PostMapping public ResponseEntity<ApiResponse<SaleResponse>> create(@RequestHeader("Idempotency-Key") String key,@Valid @RequestBody CreateSaleRequest request){SaleResponse result=idempotency.execute("POS_SALE_CREATE",key,request,SaleResponse.class,()->service.create(request));return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(result));}
 @GetMapping("/{id}") public ResponseEntity<ApiResponse<SaleResponse>> get(@PathVariable UUID id){return ResponseEntity.ok(ApiResponse.success(service.get(id)));}
 @GetMapping public ResponseEntity<ApiResponse<List<SaleResponse>>> list(@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="25") int size){int safePage=Math.max(0,page);int safeSize=Math.max(1,Math.min(size,100));var result=service.list(safePage,safeSize);return ResponseEntity.ok().header("X-Page",Integer.toString(safePage)).header("X-Page-Size",Integer.toString(safeSize)).header("X-Has-More",Boolean.toString(result.hasNext())).body(ApiResponse.success(result.getContent()));}
}
