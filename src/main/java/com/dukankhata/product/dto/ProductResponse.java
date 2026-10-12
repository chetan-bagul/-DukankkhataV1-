package com.dukankhata.product.dto;
import com.dukankhata.product.Product;
import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
public record ProductResponse(UUID id,String name,BigDecimal sellingPrice,BigDecimal averageCostPrice,String hsnCode,BigDecimal gstRate,BigDecimal currentStock,BigDecimal reorderLevel,boolean lowStock,boolean active,Instant createdAt,Instant updatedAt){public static ProductResponse from(Product p){return new ProductResponse(p.getId(),p.getName(),p.getSellingPrice(),p.getAverageCostPrice(),p.getHsnCode(),p.getGstRate(),p.getCurrentStock(),p.getReorderLevel(),p.getCurrentStock().compareTo(p.getReorderLevel())<=0,p.isActive(),p.getCreatedAt(),p.getUpdatedAt());}}
