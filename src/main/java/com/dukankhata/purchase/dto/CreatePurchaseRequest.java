package com.dukankhata.purchase.dto;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.util.*;
public record CreatePurchaseRequest(@NotNull UUID supplierId,String supplierInvoiceNumber,@NotEmpty List<@Valid PurchaseItemRequest> items,@NotNull @DecimalMin(value="0.00") BigDecimal paidAmount,String note){}
