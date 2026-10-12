package com.dukankhata.audit.dto;
import com.dukankhata.common.audit.AuditEvent;
import java.time.Instant;
import java.util.UUID;
public record AuditEventResponse(UUID id, UUID actorUserId, String actorName, String actorMobile, String resource, String action, String outcome, String entityType, String entityId, String httpMethod, String requestPath, String ipAddress, String details, Instant createdAt){
 public static AuditEventResponse from(AuditEvent e){return new AuditEventResponse(e.getId(),e.getActorUserId(),e.getActorName(),e.getActorMobile(),e.getResource(),e.getAction(),e.getOutcome(),e.getEntityType(),e.getEntityId(),e.getHttpMethod(),e.getRequestPath(),e.getIpAddress(),e.getDetails(),e.getCreatedAt());}
}
