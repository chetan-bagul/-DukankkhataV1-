package com.dukankhata.supplier.dto;
import com.dukankhata.supplier.Supplier; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
public record SupplierResponse(UUID id,String name,String mobile,String address,BigDecimal balancePayable,boolean active,Instant createdAt){public static SupplierResponse from(Supplier s){return new SupplierResponse(s.getId(),s.getName(),s.getMobile(),s.getAddress(),s.getBalancePayable(),s.isActive(),s.getCreatedAt());}}
