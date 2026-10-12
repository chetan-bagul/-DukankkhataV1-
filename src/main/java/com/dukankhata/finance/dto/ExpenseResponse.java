package com.dukankhata.finance.dto;
import com.dukankhata.finance.*; import java.math.BigDecimal; import java.time.*; import java.util.UUID;
public record ExpenseResponse(UUID id,LocalDate expenseDate,String category,String description,BigDecimal amount,boolean recurringMonthly,Instant createdAt){public static ExpenseResponse from(FinancialExpense e){return new ExpenseResponse(e.getId(),e.getExpenseDate(),e.getCategory().name(),e.getDescription(),e.getAmount(),e.isRecurringMonthly(),e.getCreatedAt());}}
