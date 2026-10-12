package com.dukankhata.supplier.dto;
import com.dukankhata.supplier.*; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
public record SupplierLedgerResponse(UUID id,SupplierLedgerType type,BigDecimal amount,BigDecimal balanceAfter,String reference,Instant createdAt){public static SupplierLedgerResponse from(SupplierLedgerEntry e){return new SupplierLedgerResponse(e.getId(),e.getType(),e.getAmount(),e.getBalanceAfter(),e.getReference(),e.getCreatedAt());}}
