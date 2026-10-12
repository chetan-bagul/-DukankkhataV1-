package com.dukankhata.staff.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record StaffRequest(@NotBlank(message="Staff name is required") @Size(max=100) String name,@Size(max=15) String mobile,@NotNull(message="Commission rate is required") @DecimalMin(value="0.00") @DecimalMax(value="100.00") BigDecimal commissionRate,Boolean active){}
