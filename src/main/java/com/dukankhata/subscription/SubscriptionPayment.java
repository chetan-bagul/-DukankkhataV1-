package com.dukankhata.subscription;

import com.dukankhata.shop.Shop;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="subscription_payments", uniqueConstraints={
 @UniqueConstraint(name="uk_sub_payment_order",columnNames="razorpay_order_id"),
 @UniqueConstraint(name="uk_sub_payment_payment",columnNames="razorpay_payment_id")})
public class SubscriptionPayment {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="shop_id",nullable=false) private Shop shop;
 @ManyToOne(optional=false,fetch=FetchType.EAGER) @JoinColumn(name="plan_id",nullable=false) private SubscriptionPlan plan;
 @Column(name="razorpay_order_id",nullable=false,length=80) private String razorpayOrderId;
 @Column(name="razorpay_payment_id",length=80) private String razorpayPaymentId;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal amount;
 @Column(nullable=false,length=3) private String currency="INR";
 @Column(nullable=false,length=20) private String status="CREATED";
 @Column(nullable=false) private Instant createdAt;
 @PrePersist void onCreate(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public SubscriptionPlan getPlan(){return plan;} public String getRazorpayOrderId(){return razorpayOrderId;} public String getRazorpayPaymentId(){return razorpayPaymentId;} public BigDecimal getAmount(){return amount;} public String getCurrency(){return currency;} public String getStatus(){return status;}
 public void setShop(Shop v){shop=v;} public void setPlan(SubscriptionPlan v){plan=v;} public void setRazorpayOrderId(String v){razorpayOrderId=v;} public void setRazorpayPaymentId(String v){razorpayPaymentId=v;} public void setAmount(BigDecimal v){amount=v;} public void setCurrency(String v){currency=v;} public void setStatus(String v){status=v;}
}
