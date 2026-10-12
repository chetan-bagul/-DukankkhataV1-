package com.dukankhata.subscription;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan,UUID>{Optional<SubscriptionPlan> findByCodeAndActiveTrue(String code); List<SubscriptionPlan> findAllByActiveTrueOrderByAmountAsc();}
