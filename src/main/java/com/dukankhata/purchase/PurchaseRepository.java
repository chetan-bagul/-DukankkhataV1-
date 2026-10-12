package com.dukankhata.purchase;
import org.springframework.data.jpa.repository.JpaRepository; import java.time.Instant; import java.util.*;
public interface PurchaseRepository extends JpaRepository<Purchase,UUID>{long countByShopIdAndCreatedAtBetween(UUID shopId,Instant from,Instant to);List<Purchase> findTop100ByShopIdOrderByCreatedAtDesc(UUID shopId);Optional<Purchase> findByIdAndShopId(UUID id,UUID shopId);}
