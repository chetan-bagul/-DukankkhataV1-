package com.dukankhata.returns;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.returns.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@AuditResource("RETURNS")
@PreAuthorize("hasRole('OWNER')")
@RestController @RequestMapping("/api/v1/returns") public class SaleReturnController { private final SaleReturnService service; private final com.dukankhata.idempotency.IdempotencyService idempotency; public SaleReturnController(SaleReturnService s, com.dukankhata.idempotency.IdempotencyService idempotency){service=s;this.idempotency=idempotency;} @PostMapping("/sales/{saleId}") public ResponseEntity<ReturnResponse> create(@PathVariable UUID saleId,@RequestHeader("Idempotency-Key") String key,@Valid @RequestBody CreateReturnRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(idempotency.execute("SALE_RETURN:"+saleId,key,r,ReturnResponse.class,()->service.create(saleId,r)));} @GetMapping public List<ReturnResponse> list(){return service.list();} }
