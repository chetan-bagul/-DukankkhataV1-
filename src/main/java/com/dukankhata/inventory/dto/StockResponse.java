package com.dukankhata.inventory.dto;
import com.dukankhata.product.Product; import java.math.BigDecimal; import java.util.UUID;
public record StockResponse(UUID productId,String productName,BigDecimal currentStock,BigDecimal reorderLevel,boolean lowStock){ public static StockResponse from(Product p){return new StockResponse(p.getId(),p.getName(),p.getCurrentStock(),p.getReorderLevel(),p.getCurrentStock().compareTo(p.getReorderLevel())<=0);}}
