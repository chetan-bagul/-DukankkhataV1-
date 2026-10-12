package com.dukankhata.staff.dto;
import com.dukankhata.staff.Staff; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
public record StaffResponse(UUID id,String name,String mobile,BigDecimal commissionRate,boolean active,BigDecimal payable,Instant createdAt){ public static StaffResponse from(Staff s,BigDecimal payable){return new StaffResponse(s.getId(),s.getName(),s.getMobile(),s.getCommissionRate(),s.isActive(),payable,s.getCreatedAt());}}
