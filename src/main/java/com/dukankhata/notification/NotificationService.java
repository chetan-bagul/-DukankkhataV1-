package com.dukankhata.notification;

import com.dukankhata.auth.User;
import com.dukankhata.common.exception.*;
import com.dukankhata.common.security.CurrentUserService;
import com.dukankhata.notification.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant; import java.util.*;

@Service
public class NotificationService {
 private final NotificationRepository repo; private final CurrentUserService current; private final NotificationOutboxRepository outbox;
 public NotificationService(NotificationRepository repo,CurrentUserService current,NotificationOutboxRepository outbox){this.repo=repo;this.current=current;this.outbox=outbox;}
 @Transactional public NotificationResponse createAndSend(CreateNotificationRequest r){
  User u=current.requireUser(); return enqueue(u.getShop(),r.channel(),r.type(),r.recipient(),r.subject(),r.message(),"manual:"+UUID.randomUUID());
 }
 @Transactional public NotificationResponse automated(com.dukankhata.shop.Shop shop, NotificationChannel channel, NotificationType type, String recipient, String subject, String message){
  String key="auto:"+type+":"+UUID.randomUUID(); return enqueue(shop,channel,type,recipient,subject,message,key);
 }
 @Transactional(readOnly=true) public List<NotificationResponse> latest(){return repo.findTop100ByShopIdOrderByCreatedAtDesc(current.shopId()).stream().map(NotificationResponse::from).toList();}
 @Transactional public NotificationResponse retry(UUID id){Notification n=repo.findByIdAndShopId(id,current.shopId()).orElseThrow(()->new ResourceNotFoundException("Notification not found")); if(n.getStatus()==NotificationStatus.SENT) throw new BusinessException("Notification was already sent");
  NotificationOutbox o=outbox.findByEventKey("retry:"+id+":"+UUID.randomUUID()).orElse(null); // intentionally create a fresh delivery event
  NotificationOutbox fresh=new NotificationOutbox(); fresh.setEventKey("retry:"+id+":"+UUID.randomUUID()); fresh.setNotification(n); fresh.setStatus(NotificationOutboxStatus.PENDING); fresh.setAttempts(0); fresh.setNextAttemptAt(Instant.now()); outbox.save(fresh); n.setStatus(NotificationStatus.QUEUED); n.setLastError(null); return NotificationResponse.from(repo.save(n));
 }
 private NotificationResponse enqueue(com.dukankhata.shop.Shop shop,NotificationChannel channel,NotificationType type,String recipient,String subject,String message,String eventKey){
  Notification n=new Notification(); n.setShop(shop); n.setChannel(channel); n.setType(type); n.setRecipient(recipient.trim()); n.setSubject(subject==null?"":subject.trim()); n.setMessage(message); n.setProvider("MOCK"); n.setStatus(NotificationStatus.QUEUED); n.setAttempts(0); n=repo.save(n);
  NotificationOutbox o=new NotificationOutbox(); o.setEventKey(eventKey); o.setNotification(n); o.setStatus(NotificationOutboxStatus.PENDING); o.setAttempts(0); o.setNextAttemptAt(Instant.now()); outbox.save(o);
  return NotificationResponse.from(n);
 }
}
