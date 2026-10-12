package com.dukankhata.subscription;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment,UUID>{Optional<SubscriptionPayment> findByRazorpayOrderId(String id); Optional<SubscriptionPayment> findByRazorpayPaymentId(String id);}
