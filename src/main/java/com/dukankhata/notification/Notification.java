package com.dukankhata.notification;

import com.dukankhata.shop.Shop;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="notification_messages", indexes={
 @Index(name="idx_notification_shop_created", columnList="shop_id,created_at"),
 @Index(name="idx_notification_status", columnList="status")})
public class Notification {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="shop_id",nullable=false) private Shop shop;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private NotificationChannel channel;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=40) private NotificationType type;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private NotificationStatus status;
 @Column(nullable=false,length=40) private String recipient;
 @Column(nullable=false,length=200) private String subject;
 @Column(nullable=false,columnDefinition="text") private String message;
 @Column(name="provider",nullable=false,length=30) private String provider="MOCK";
 @Column(name="provider_message_id",length=120) private String providerMessageId;
 @Column(nullable=false) private Integer attempts=0;
 @Column(name="last_error",length=500) private String lastError;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 private Instant sentAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public void setShop(Shop v){shop=v;}
 public NotificationChannel getChannel(){return channel;} public void setChannel(NotificationChannel v){channel=v;}
 public NotificationType getType(){return type;} public void setType(NotificationType v){type=v;}
 public NotificationStatus getStatus(){return status;} public void setStatus(NotificationStatus v){status=v;}
 public String getRecipient(){return recipient;} public void setRecipient(String v){recipient=v;}
 public String getSubject(){return subject;} public void setSubject(String v){subject=v;}
 public String getMessage(){return message;} public void setMessage(String v){message=v;}
 public String getProvider(){return provider;} public void setProvider(String v){provider=v;}
 public String getProviderMessageId(){return providerMessageId;} public void setProviderMessageId(String v){providerMessageId=v;}
 public Integer getAttempts(){return attempts;} public void setAttempts(Integer v){attempts=v;}
 public String getLastError(){return lastError;} public void setLastError(String v){lastError=v;}
 public Instant getCreatedAt(){return createdAt;} public Instant getSentAt(){return sentAt;} public void setSentAt(Instant v){sentAt=v;}
}
