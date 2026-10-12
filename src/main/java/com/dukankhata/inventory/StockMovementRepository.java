package com.dukankhata.inventory;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface StockMovementRepository extends JpaRepository<StockMovement,UUID> { List<StockMovement> findTop100ByShopIdAndProductIdOrderByCreatedAtDesc(UUID shopId,UUID productId); }
