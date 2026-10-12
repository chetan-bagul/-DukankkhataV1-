package com.dukankhata.customer;

import com.dukankhata.shop.Shop;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "customers",
       uniqueConstraints = @UniqueConstraint(name = "uk_customer_shop_number", columnNames = {"shop_id", "customer_number"}),
       indexes = @Index(name = "idx_customer_shop_name", columnList = "shop_id,name"))
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(name = "customer_number", nullable = false)
    private Integer customerNumber;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 15)
    private String mobile;

    @Column(length = 300)
    private String address;

    @Column(name = "gstin", length = 15)
    private String gstin;

    @Column(name = "state_code", length = 2)
    private String stateCode;

    /** Positive = advance available; negative = amount customer owes. */
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist void onCreate() { Instant now = Instant.now(); createdAt = now; updatedAt = now; }
    @PreUpdate void onUpdate() { updatedAt = Instant.now(); }

    public UUID getId(){return id;} public Shop getShop(){return shop;} public Integer getCustomerNumber(){return customerNumber;}
    public String getName(){return name;} public String getMobile(){return mobile;} public String getAddress(){return address;} public String getGstin(){return gstin;} public String getStateCode(){return stateCode;}
    public BigDecimal getBalance(){return balance;} public boolean isActive(){return active;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
    public void setShop(Shop v){shop=v;} public void setCustomerNumber(Integer v){customerNumber=v;} public void setName(String v){name=v;}
    public void setMobile(String v){mobile=v;} public void setAddress(String v){address=v;} public void setGstin(String v){gstin=v;} public void setStateCode(String v){stateCode=v;} public void setBalance(BigDecimal v){balance=v;} public void setActive(boolean v){active=v;}
}
