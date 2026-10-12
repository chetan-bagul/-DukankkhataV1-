package com.dukankhata.purchase.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.util.UUID;
public record PurchaseItemRequest(@NotNull UUID productId,@NotNull @DecimalMin(value="0.001",message="Quantity must be greater than zero") BigDecimal quantity,@NotNull @DecimalMin(value="0.01",message="Unit cost must be greater than zero") BigDecimal unitCost){}
