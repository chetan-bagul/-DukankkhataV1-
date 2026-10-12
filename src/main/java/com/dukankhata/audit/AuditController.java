package com.dukankhata.audit;
import com.dukankhata.audit.dto.AuditEventResponse;
import com.dukankhata.common.api.ApiResponse;
import com.dukankhata.common.audit.AuditService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@PreAuthorize("hasRole('OWNER')")
@RestController
@RequestMapping("/api/v1/audit")
public class AuditController{
 private final AuditService audit;
 public AuditController(AuditService audit){this.audit=audit;}
 @GetMapping public ApiResponse<List<AuditEventResponse>> latest(@RequestParam(defaultValue="100") int limit){return ApiResponse.ok(audit.latest(limit).stream().map(AuditEventResponse::from).toList());}
}
