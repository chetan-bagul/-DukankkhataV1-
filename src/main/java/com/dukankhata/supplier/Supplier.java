package com.dukankhata.supplier;

import com.dukankhata.shop.Shop;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="suppliers", indexes={@Index(name="idx_supplier_shop_name", columnList="shop_id,name")})
public class Supplier {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="shop_id", nullable=false) private Shop shop;
 @Column(nullable=false,length=140) private String name;
 @Column(length=20) private String mobile;
 @Column(length=200) private String address;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal balancePayable=BigDecimal.ZERO;
 @Column(nullable=false) private boolean active=true;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @Column(nullable=false) private Instant updatedAt;
 @PrePersist void create(){Instant n=Instant.now();createdAt=n;updatedAt=n;}
 @PreUpdate void update(){updatedAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public String getName(){return name;} public String getMobile(){return mobile;} public String getAddress(){return address;} public BigDecimal getBalancePayable(){return balancePayable;} public boolean isActive(){return active;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
 public void setShop(Shop v){shop=v;} public void setName(String v){name=v;} public void setMobile(String v){mobile=v;} public void setAddress(String v){address=v;} public void setBalancePayable(BigDecimal v){balancePayable=v;} public void setActive(boolean v){active=v;}
}
