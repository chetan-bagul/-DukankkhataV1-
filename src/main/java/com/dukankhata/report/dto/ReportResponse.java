package com.dukankhata.report.dto;

import java.util.List;

public record ReportResponse(ReportSummaryResponse summary, List<DailyReportRow> days) {}
