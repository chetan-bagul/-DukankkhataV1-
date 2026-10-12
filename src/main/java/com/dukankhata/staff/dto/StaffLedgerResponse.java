package com.dukankhata.staff.dto;
import com.dukankhata.staff.StaffLedgerEntry; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
public record StaffLedgerResponse(UUID id,String type,BigDecimal amount,String description,UUID referenceId,Instant createdAt){public static StaffLedgerResponse from(StaffLedgerEntry e){return new StaffLedgerResponse(e.getId(),e.getType().name(),e.getAmount(),e.getDescription(),e.getReferenceId(),e.getCreatedAt());}}
