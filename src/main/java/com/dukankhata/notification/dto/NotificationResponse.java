package com.dukankhata.notification.dto;
import com.dukankhata.notification.*; import java.time.Instant; import java.util.UUID;
public record NotificationResponse(UUID id,NotificationChannel channel,NotificationType type,NotificationStatus status,String recipient,String subject,String message,String provider,Integer attempts,String lastError,Instant createdAt,Instant sentAt){
 public static NotificationResponse from(Notification n){return new NotificationResponse(n.getId(),n.getChannel(),n.getType(),n.getStatus(),n.getRecipient(),n.getSubject(),n.getMessage(),n.getProvider(),n.getAttempts(),n.getLastError(),n.getCreatedAt(),n.getSentAt());}
}
