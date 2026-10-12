package com.dukankhata.subscription;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SubscriptionRepository extends JpaRepository<Subscription,UUID>{Optional<Subscription> findTopByShopIdOrderByCreatedAtDesc(UUID shopId); Optional<Subscription> findByRazorpayOrderId(String orderId);}
