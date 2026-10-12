package com.dukankhata.report;
import com.dukankhata.common.audit.AuditResource;

import com.dukankhata.common.api.ApiResponse;
import com.dukankhata.report.dto.ReportResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@AuditResource("REPORT")
@PreAuthorize("hasRole('OWNER')")
@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {
    private final ReportService service;
    public ReportController(ReportService service){this.service=service;}

    @GetMapping("/summary")
    public ApiResponse<ReportResponse> summary(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate to){
        return ApiResponse.ok(service.report(from,to));
    }

    @GetMapping(value="/pdf", produces=MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdf(
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate to){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=dukankhata-report-"+from+"-to-"+to+".pdf")
                .body(service.pdf(from,to));
    }
}
