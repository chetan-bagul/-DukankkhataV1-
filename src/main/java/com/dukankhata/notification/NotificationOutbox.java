package com.dukankhata.notification;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="notification_outbox", indexes={
 @Index(name="idx_outbox_ready", columnList="status,next_attempt_at"),
 @Index(name="idx_outbox_notification", columnList="notification_id")},
 uniqueConstraints=@UniqueConstraint(name="uk_outbox_event_key", columnNames="event_key"))
public class NotificationOutbox {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(name="event_key",nullable=false,length=160) private String eventKey;
 @OneToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="notification_id",nullable=false,unique=true) private Notification notification;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private NotificationOutboxStatus status=NotificationOutboxStatus.PENDING;
 @Column(nullable=false) private Integer attempts=0;
 @Column(name="next_attempt_at",nullable=false) private Instant nextAttemptAt;
 @Column(name="locked_at") private Instant lockedAt;
 @Column(name="completed_at") private Instant completedAt;
 @Column(name="last_error",length=500) private String lastError;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now(); if(nextAttemptAt==null) nextAttemptAt=createdAt;}
 public UUID getId(){return id;} public String getEventKey(){return eventKey;} public void setEventKey(String v){eventKey=v;}
 public Notification getNotification(){return notification;} public void setNotification(Notification v){notification=v;}
 public NotificationOutboxStatus getStatus(){return status;} public void setStatus(NotificationOutboxStatus v){status=v;}
 public Integer getAttempts(){return attempts;} public void setAttempts(Integer v){attempts=v;}
 public Instant getNextAttemptAt(){return nextAttemptAt;} public void setNextAttemptAt(Instant v){nextAttemptAt=v;}
 public Instant getLockedAt(){return lockedAt;} public void setLockedAt(Instant v){lockedAt=v;}
 public Instant getCompletedAt(){return completedAt;} public void setCompletedAt(Instant v){completedAt=v;}
 public String getLastError(){return lastError;} public void setLastError(String v){lastError=v;}
 public Instant getCreatedAt(){return createdAt;}
}
