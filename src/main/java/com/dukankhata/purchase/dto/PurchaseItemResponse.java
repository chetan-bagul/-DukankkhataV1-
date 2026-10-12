package com.dukankhata.purchase.dto;
import com.dukankhata.purchase.PurchaseItem; import java.math.BigDecimal; import java.util.UUID;
public record PurchaseItemResponse(UUID productId,String productName,BigDecimal quantity,BigDecimal unitCost,BigDecimal lineTotal){public static PurchaseItemResponse from(PurchaseItem i){return new PurchaseItemResponse(i.getProduct().getId(),i.getProductName(),i.getQuantity(),i.getUnitCost(),i.getLineTotal());}}
