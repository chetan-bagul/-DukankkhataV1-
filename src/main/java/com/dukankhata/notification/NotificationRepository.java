package com.dukankhata.notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface NotificationRepository extends JpaRepository<Notification,UUID>{
 List<Notification> findTop100ByShopIdOrderByCreatedAtDesc(UUID shopId);
 Optional<Notification> findByIdAndShopId(UUID id, UUID shopId);
}
