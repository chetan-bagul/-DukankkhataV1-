package com.dukankhata.notification;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.data.domain.PageRequest;
import java.time.*;
import java.util.*;

@Component
public class NotificationOutboxWorker {
 private static final int MAX_ATTEMPTS=5;
 private final NotificationOutboxRepository outbox; private final NotificationRepository notifications; private final NotificationProvider provider; private final TransactionTemplate transactionTemplate;
 public NotificationOutboxWorker(NotificationOutboxRepository outbox,NotificationRepository notifications,NotificationProvider provider,TransactionTemplate transactionTemplate){this.outbox=outbox;this.notifications=notifications;this.provider=provider;this.transactionTemplate=transactionTemplate;}
 @Scheduled(fixedDelayString="${notifications.worker.fixed-delay-ms:5000}")
 public void process(){for(int i=0;i<10;i++){UUID id=claimOne();if(id==null)return;deliver(id);}}
 UUID claimOne(){return transactionTemplate.execute(status->{List<NotificationOutbox> rows=outbox.lockReady(NotificationOutboxStatus.PENDING,Instant.now(),PageRequest.of(0,1));if(rows.isEmpty())return null;NotificationOutbox o=rows.get(0);o.setStatus(NotificationOutboxStatus.PROCESSING);o.setLockedAt(Instant.now());o.setAttempts(o.getAttempts()+1);Notification n=o.getNotification();n.setStatus(NotificationStatus.PROCESSING);notifications.save(n);outbox.save(o);return o.getId();});}
 void deliver(UUID id){NotificationOutbox o=outbox.findWithNotificationById(id).orElse(null);if(o==null)return;Notification n=o.getNotification();NotificationProvider.DeliveryResult result;try{result=provider.send(n,o.getEventKey());}catch(Exception ex){result=new NotificationProvider.DeliveryResult(false,null,ex.getMessage()==null?"Provider error":ex.getMessage());}finish(id,result);}
 void finish(UUID id,NotificationProvider.DeliveryResult result){transactionTemplate.executeWithoutResult(status->{NotificationOutbox o=outbox.findWithNotificationById(id).orElse(null);if(o==null)return;Notification n=o.getNotification();if(result.success()){n.setStatus(NotificationStatus.SENT);n.setProviderMessageId(result.providerMessageId());n.setLastError(null);n.setSentAt(Instant.now());o.setStatus(NotificationOutboxStatus.COMPLETED);o.setCompletedAt(Instant.now());o.setLastError(null);}else{String error=result.error()==null?"Delivery failed":result.error();n.setLastError(error);if(o.getAttempts()>=MAX_ATTEMPTS){n.setStatus(NotificationStatus.DEAD_LETTER);o.setStatus(NotificationOutboxStatus.DEAD_LETTER);}else{n.setStatus(NotificationStatus.FAILED);o.setStatus(NotificationOutboxStatus.PENDING);o.setNextAttemptAt(Instant.now().plus(backoff(o.getAttempts())));}o.setLastError(error);}notifications.save(n);outbox.save(o);});}
 private Duration backoff(int attempt){return Duration.ofSeconds(Math.min(300L,5L*(1L<<Math.min(6,Math.max(0,attempt-1)))));}
}
