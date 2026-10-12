package com.dukankhata.pos.dto;
import com.dukankhata.pos.PaymentMethod; import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record SalePaymentRequest(@NotNull(message="Payment method is required") PaymentMethod method,@NotNull(message="Payment amount is required") @DecimalMin(value="0.01",message="Payment amount must be greater than 0") BigDecimal amount) {}
