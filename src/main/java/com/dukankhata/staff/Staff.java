package com.dukankhata.staff;

import com.dukankhata.shop.Shop;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="staff", indexes={@Index(name="idx_staff_shop_active", columnList="shop_id,active")})
public class Staff {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="shop_id",nullable=false) private Shop shop;
 @Column(nullable=false,length=100) private String name;
 @Column(length=15) private String mobile;
 @Column(nullable=false,precision=5,scale=2) private BigDecimal commissionRate=BigDecimal.ZERO;
 @Column(nullable=false) private boolean active=true;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @Column(nullable=false) private Instant updatedAt;
 @Version private long version;
 @PrePersist void create(){Instant n=Instant.now();createdAt=n;updatedAt=n;}
 @PreUpdate void update(){updatedAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public String getName(){return name;} public String getMobile(){return mobile;} public BigDecimal getCommissionRate(){return commissionRate;} public boolean isActive(){return active;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
 public void setShop(Shop v){shop=v;} public void setName(String v){name=v;} public void setMobile(String v){mobile=v;} public void setCommissionRate(BigDecimal v){commissionRate=v;} public void setActive(boolean v){active=v;}
}
