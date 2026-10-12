package com.dukankhata.product.dto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
public record ProductRequest(@NotBlank(message="Product name is required") String name,@NotNull(message="Selling price is required") @DecimalMin(value="0.01",message="Selling price must be greater than zero") BigDecimal sellingPrice,BigDecimal averageCostPrice,@DecimalMin(value="0.00") BigDecimal reorderLevel,String hsnCode,@DecimalMin(value="0.00") @jakarta.validation.constraints.DecimalMax(value="100.00") BigDecimal gstRate) {}
