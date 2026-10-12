package com.dukankhata.notification;
import org.springframework.stereotype.Component;
@Component
public class MockNotificationProvider implements NotificationProvider {
 public DeliveryResult send(Notification n){ return new DeliveryResult(true,"mock-"+n.getId(),null); }
 public DeliveryResult send(Notification n,String idempotencyKey){ return new DeliveryResult(true,"mock-"+idempotencyKey,null); }
}
