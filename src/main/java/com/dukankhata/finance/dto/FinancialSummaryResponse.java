package com.dukankhata.finance.dto;
import java.math.BigDecimal; import java.time.LocalDate;
public record FinancialSummaryResponse(LocalDate from,LocalDate to,long salesCount,BigDecimal revenue,BigDecimal cogs,BigDecimal grossProfit,BigDecimal grossMarginPercent,BigDecimal closingExpenses,BigDecimal otherExpenses,BigDecimal commissionExpenses,BigDecimal totalExpenses,BigDecimal netProfit,BigDecimal netMarginPercent){}
