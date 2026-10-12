package com.dukankhata.subscription;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class SubscriptionDataInitializer {
 @Bean CommandLineRunner seedPlans(
         SubscriptionPlanRepository repo,
         @Value("${subscription.plans.small-monthly:150}") BigDecimal smallMonthly,
         @Value("${subscription.plans.small-yearly:1500}") BigDecimal smallYearly,
         @Value("${subscription.plans.large-monthly:299}") BigDecimal largeMonthly,
         @Value("${subscription.plans.large-yearly:2999}") BigDecimal largeYearly) {
  return args -> {
   seed(repo,"SMALL_MONTHLY","Small Shop Monthly",smallMonthly,30);
   seed(repo,"SMALL_YEARLY","Small Shop Yearly",smallYearly,365);
   seed(repo,"LARGE_MONTHLY","Large Shop Monthly",largeMonthly,30);
   seed(repo,"LARGE_YEARLY","Large Shop Yearly",largeYearly,365);
  };
 }

 private void seed(SubscriptionPlanRepository repo,String code,String name,BigDecimal amount,int durationDays){
  SubscriptionPlan plan=repo.findByCodeAndActiveTrue(code).orElseGet(SubscriptionPlan::new);
  plan.setCode(code);
  plan.setName(name);
  plan.setAmount(amount);
  plan.setCurrency("INR");
  plan.setDurationDays(durationDays);
  plan.setActive(true);
  repo.save(plan);
 }
}
