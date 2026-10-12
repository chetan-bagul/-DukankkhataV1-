package com.dukankhata.finance.dto;
import com.dukankhata.finance.ExpenseCategory;
import jakarta.validation.constraints.*;
import java.math.BigDecimal; import java.time.LocalDate;
public record ExpenseRequest(@NotNull LocalDate expenseDate,@NotNull ExpenseCategory category,@NotBlank @Size(max=180) String description,@NotNull @DecimalMin(value="0.01") BigDecimal amount,boolean recurringMonthly){}
