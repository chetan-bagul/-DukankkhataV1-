package com.dukankhata.inventory.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record StockReceiptRequest(@NotNull @DecimalMin(value="0.001") BigDecimal quantity,@Size(max=300) String note) {}
