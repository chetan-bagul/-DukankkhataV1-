package com.dukankhata.notification;
public interface NotificationProvider {
 DeliveryResult send(Notification n);
 default DeliveryResult send(Notification n,String idempotencyKey){return send(n);}
 record DeliveryResult(boolean success,String providerMessageId,String error){}
}
