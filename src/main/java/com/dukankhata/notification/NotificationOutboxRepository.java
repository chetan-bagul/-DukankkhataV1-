package com.dukankhata.notification;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import java.util.*;

public interface NotificationOutboxRepository extends JpaRepository<NotificationOutbox,UUID> {
 Optional<NotificationOutbox> findByEventKey(String eventKey);
 @EntityGraph(attributePaths="notification")
 Optional<NotificationOutbox> findWithNotificationById(UUID id);
 @Lock(LockModeType.PESSIMISTIC_WRITE)
 @Query("select o from NotificationOutbox o where o.status = :status and o.nextAttemptAt <= :now order by o.createdAt asc")
 List<NotificationOutbox> lockReady(@Param("status") NotificationOutboxStatus status,@Param("now") Instant now,org.springframework.data.domain.Pageable pageable);
}
