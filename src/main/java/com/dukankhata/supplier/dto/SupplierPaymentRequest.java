package com.dukankhata.supplier.dto;
import jakarta.validation.constraints.DecimalMin; import jakarta.validation.constraints.NotNull; import java.math.BigDecimal;
public record SupplierPaymentRequest(@NotNull @DecimalMin(value="0.01",message="Payment must be greater than zero") BigDecimal amount,String reference){}
