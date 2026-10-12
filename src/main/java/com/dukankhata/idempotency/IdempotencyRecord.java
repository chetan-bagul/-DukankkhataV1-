package com.dukankhata.idempotency;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "idempotency_records", uniqueConstraints = {
        @UniqueConstraint(name = "uk_idempotency_scope", columnNames = {"shop_id", "user_id", "operation", "idempotency_key"})
})
public class IdempotencyRecord {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name="shop_id", nullable=false) private UUID shopId;
    @Column(name="user_id", nullable=false) private UUID userId;
    @Column(nullable=false, length=80) private String operation;
    @Column(name="idempotency_key", nullable=false, length=128) private String idempotencyKey;
    @Column(name="request_hash", nullable=false, length=64) private String requestHash;
    @Column(name="response_json", nullable=false, columnDefinition="text") private String responseJson;
    @Column(nullable=false, updatable=false) private Instant createdAt;

    @PrePersist void onCreate(){ createdAt=Instant.now(); }
    public UUID getId(){return id;} public UUID getShopId(){return shopId;} public UUID getUserId(){return userId;}
    public String getOperation(){return operation;} public String getIdempotencyKey(){return idempotencyKey;} public String getRequestHash(){return requestHash;}
    public String getResponseJson(){return responseJson;} public Instant getCreatedAt(){return createdAt;}
    public void setShopId(UUID v){shopId=v;} public void setUserId(UUID v){userId=v;} public void setOperation(String v){operation=v;}
    public void setIdempotencyKey(String v){idempotencyKey=v;} public void setRequestHash(String v){requestHash=v;} public void setResponseJson(String v){responseJson=v;}
}
