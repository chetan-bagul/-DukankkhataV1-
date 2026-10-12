package com.dukankhata.pos.dto;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.util.*;
public record CreateSaleRequest(UUID customerId, UUID sellerId,@NotEmpty(message="At least one sale item is required") @Valid List<SaleItemRequest> items,@NotEmpty(message="At least one payment is required") @Valid List<SalePaymentRequest> payments) {}
