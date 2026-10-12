package com.dukankhata.staff;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.staff.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.util.*;
@AuditResource("STAFF")
@PreAuthorize("hasRole('OWNER')")
@RestController @RequestMapping("/api/v1/staff") public class StaffController{
 private final StaffService service; private final com.dukankhata.idempotency.IdempotencyService idempotency; public StaffController(StaffService s, com.dukankhata.idempotency.IdempotencyService idempotency){service=s;this.idempotency=idempotency;}
 @GetMapping public List<StaffResponse> list(){return service.list();}
 @PostMapping public ResponseEntity<StaffResponse> create(@Valid @RequestBody StaffRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));}
 @PutMapping("/{id}") public StaffResponse update(@PathVariable UUID id,@Valid @RequestBody StaffRequest r){return service.update(id,r);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable UUID id){service.delete(id);return ResponseEntity.noContent().build();}
 @GetMapping("/{id}/ledger") public List<StaffLedgerResponse> ledger(@PathVariable UUID id){return service.ledger(id);}
 @PostMapping("/{id}/payouts") public StaffLedgerResponse payout(@PathVariable UUID id,@RequestHeader("Idempotency-Key") String key,@Valid @RequestBody StaffPayoutRequest r){return idempotency.execute("STAFF_PAYOUT:"+id,key,r,StaffLedgerResponse.class,()->service.payout(id,r));}
 @PostMapping("/{id}/adjustments") public StaffLedgerResponse adjustment(@PathVariable UUID id,@Valid @RequestBody StaffAdjustmentRequest r){return service.adjustment(id,r);}
}
