package com.dukankhata.subscription;

import com.dukankhata.shop.Shop;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="subscriptions", indexes=@Index(name="idx_subscription_shop", columnList="shop_id"))
public class Subscription {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="shop_id",nullable=false) private Shop shop;
 @ManyToOne(optional=false,fetch=FetchType.EAGER) @JoinColumn(name="plan_id",nullable=false) private SubscriptionPlan plan;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private SubscriptionStatus status;
 @Column(name="starts_at") private Instant startsAt;
 @Column(name="ends_at") private Instant endsAt;
 @Column(name="razorpay_order_id",length=80) private String razorpayOrderId;
 @Column(name="razorpay_payment_id",length=80) private String razorpayPaymentId;
 @Column(nullable=false) private Instant createdAt;
 @PrePersist void onCreate(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public SubscriptionPlan getPlan(){return plan;} public SubscriptionStatus getStatus(){return status;} public Instant getStartsAt(){return startsAt;} public Instant getEndsAt(){return endsAt;} public String getRazorpayOrderId(){return razorpayOrderId;} public String getRazorpayPaymentId(){return razorpayPaymentId;} public Instant getCreatedAt(){return createdAt;}
 public void setShop(Shop v){shop=v;} public void setPlan(SubscriptionPlan v){plan=v;} public void setStatus(SubscriptionStatus v){status=v;} public void setStartsAt(Instant v){startsAt=v;} public void setEndsAt(Instant v){endsAt=v;} public void setRazorpayOrderId(String v){razorpayOrderId=v;} public void setRazorpayPaymentId(String v){razorpayPaymentId=v;}
}
