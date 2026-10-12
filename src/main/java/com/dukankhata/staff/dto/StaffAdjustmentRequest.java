package com.dukankhata.staff.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record StaffAdjustmentRequest(@NotNull @DecimalMin(value="-1000000.00") @DecimalMax(value="1000000.00") BigDecimal amount,@NotBlank @Size(max=500) String description){}
