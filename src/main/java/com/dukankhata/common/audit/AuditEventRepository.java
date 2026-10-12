package com.dukankhata.common.audit;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {
    List<AuditEvent> findByShopIdOrderByCreatedAtDesc(UUID shopId, Pageable pageable);
    List<AuditEvent> findByShopIdAndCreatedAtBetweenOrderByCreatedAtDesc(UUID shopId, Instant from, Instant to, Pageable pageable);
}
