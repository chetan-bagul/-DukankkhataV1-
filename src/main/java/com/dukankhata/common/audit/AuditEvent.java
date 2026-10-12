package com.dukankhata.common.audit;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_events", indexes = {
        @Index(name = "idx_audit_shop_time", columnList = "shop_id,created_at"),
        @Index(name = "idx_audit_actor_time", columnList = "actor_user_id,created_at"),
        @Index(name = "idx_audit_entity", columnList = "entity_type,entity_id")
})
public class AuditEvent {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="shop_id") private UUID shopId;
    @Column(name="actor_user_id") private UUID actorUserId;
    @Column(length=100) private String actorName;
    @Column(length=20) private String actorMobile;
    @Column(nullable=false,length=60) private String resource;
    @Column(nullable=false,length=100) private String action;
    @Column(nullable=false,length=20) private String outcome;
    @Column(name="entity_type",length=80) private String entityType;
    @Column(name="entity_id",length=80) private String entityId;
    @Column(length=80) private String httpMethod;
    @Column(length=300) private String requestPath;
    @Column(length=80) private String ipAddress;
    @Column(length=300) private String userAgent;
    @Column(length=1000) private String details;
    @Column(nullable=false,updatable=false) private Instant createdAt;
    @PrePersist void onCreate(){createdAt=Instant.now();}
    public UUID getId(){return id;} public UUID getShopId(){return shopId;} public UUID getActorUserId(){return actorUserId;}
    public String getActorName(){return actorName;} public String getActorMobile(){return actorMobile;} public String getResource(){return resource;}
    public String getAction(){return action;} public String getOutcome(){return outcome;} public String getEntityType(){return entityType;}
    public String getEntityId(){return entityId;} public String getHttpMethod(){return httpMethod;} public String getRequestPath(){return requestPath;}
    public String getIpAddress(){return ipAddress;} public String getUserAgent(){return userAgent;} public String getDetails(){return details;} public Instant getCreatedAt(){return createdAt;}
    public void setShopId(UUID v){shopId=v;} public void setActorUserId(UUID v){actorUserId=v;} public void setActorName(String v){actorName=v;}
    public void setActorMobile(String v){actorMobile=v;} public void setResource(String v){resource=v;} public void setAction(String v){action=v;}
    public void setOutcome(String v){outcome=v;} public void setEntityType(String v){entityType=v;} public void setEntityId(String v){entityId=v;}
    public void setHttpMethod(String v){httpMethod=v;} public void setRequestPath(String v){requestPath=v;} public void setIpAddress(String v){ipAddress=v;}
    public void setUserAgent(String v){userAgent=v;} public void setDetails(String v){details=v;}
}
