package com.dukankhata.notification.dto;
import com.dukankhata.notification.*;
import jakarta.validation.constraints.*;
public record CreateNotificationRequest(@NotNull NotificationChannel channel,@NotNull NotificationType type,@NotBlank @Size(max=40) String recipient,@NotBlank @Size(max=200) String subject,@NotBlank @Size(max=10000) String message){}
