package com.dukankhata.closing;
import com.dukankhata.common.audit.AuditResource;

import com.dukankhata.closing.dto.ClosingRequest;
import com.dukankhata.closing.dto.ClosingResponse;
import com.dukankhata.common.api.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AuditResource("CLOSING")
@PreAuthorize("hasRole('OWNER')")
@RestController
@RequestMapping("/api/v1/closings")
public class DailyClosingController {
    private final DailyClosingService service;

    public DailyClosingController(DailyClosingService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<ClosingResponse>> list() {
        return ApiResponse.ok(service.list());
    }

    @GetMapping("/{id}")
    public ApiResponse<ClosingResponse> get(@PathVariable UUID id) {
        return ApiResponse.ok(service.get(id));
    }

    @PostMapping
    public ApiResponse<ClosingResponse> save(@Valid @RequestBody ClosingRequest request) {
        return ApiResponse.ok(service.save(request));
    }

    @PostMapping("/{id}/close")
    public ApiResponse<ClosingResponse> close(@PathVariable UUID id) {
        return ApiResponse.ok(service.close(id));
    }

 @PostMapping("/{id}/sync-pos") public ApiResponse<ClosingResponse> syncPos(@PathVariable UUID id){return ApiResponse.ok(service.syncPos(id));}
}
