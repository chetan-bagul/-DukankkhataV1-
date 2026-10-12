package com.dukankhata.finance;
import com.dukankhata.common.audit.AuditResource;
import com.dukankhata.finance.dto.*; import jakarta.validation.Valid; import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat; import org.springframework.http.ResponseEntity; import org.springframework.web.bind.annotation.*; import java.time.LocalDate; import java.util.*;
@AuditResource("FINANCE")
@PreAuthorize("hasRole('OWNER')")
@RestController @RequestMapping("/api/v1/finance")
public class FinancialController {
 private final FinancialService service; public FinancialController(FinancialService s){service=s;}
 @PostMapping("/expenses") public ResponseEntity<?> add(@Valid @RequestBody ExpenseRequest r){return ResponseEntity.ok(service.addExpense(r));}
 @GetMapping("/expenses") public ResponseEntity<?> expenses(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate from,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate to){return ResponseEntity.ok(service.listExpenses(from,to));}
 @DeleteMapping("/expenses/{id}") public ResponseEntity<?> delete(@PathVariable UUID id){service.deleteExpense(id);return ResponseEntity.noContent().build();}
 @GetMapping("/summary") public ResponseEntity<FinancialSummaryResponse> summary(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate from,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate to){return ResponseEntity.ok(service.summary(from,to));}
}
