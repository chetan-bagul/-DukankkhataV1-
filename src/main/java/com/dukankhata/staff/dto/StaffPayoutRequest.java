package com.dukankhata.staff.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record StaffPayoutRequest(@NotNull @DecimalMin(value="0.01") BigDecimal amount,@NotBlank @Size(max=500) String description){}
