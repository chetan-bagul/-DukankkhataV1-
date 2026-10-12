package com.dukankhata.idempotency;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface IdempotencyRepository extends JpaRepository<IdempotencyRecord, UUID> {
    Optional<IdempotencyRecord> findByShopIdAndUserIdAndOperationAndIdempotencyKey(UUID shopId, UUID userId, String operation, String idempotencyKey);
}
