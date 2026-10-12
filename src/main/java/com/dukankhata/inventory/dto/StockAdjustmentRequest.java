package com.dukankhata.inventory.dto;
import jakarta.validation.constraints.*; import java.math.BigDecimal;
public record StockAdjustmentRequest(@NotNull @DecimalMin(value="0.001") BigDecimal quantity,@NotNull AdjustmentDirection direction,@Size(max=300) String note) { public enum AdjustmentDirection { IN, OUT } }
