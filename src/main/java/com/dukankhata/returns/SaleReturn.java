package com.dukankhata.returns;
import com.dukankhata.pos.Sale; import com.dukankhata.shop.Shop;
import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.util.*;
@Entity @Table(name="sale_returns", indexes={@Index(name="idx_sale_return_shop_created",columnList="shop_id,created_at"),@Index(name="idx_sale_return_sale",columnList="sale_id")}, uniqueConstraints=@UniqueConstraint(name="uk_sale_return_sale",columnNames={"sale_id"}))
public class SaleReturn {
 @Id @GeneratedValue(strategy=GenerationType.UUID) UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="shop_id",nullable=false) Shop shop;
 @OneToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="sale_id",nullable=false) Sale sale;
 @Column(nullable=false,precision=14,scale=2) BigDecimal refundTotal;
 @Column(nullable=false,length=500) String reason;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) ReturnStatus status=ReturnStatus.COMPLETED;
 @Column(nullable=false,updatable=false) Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public Sale getSale(){return sale;} public BigDecimal getRefundTotal(){return refundTotal;} public String getReason(){return reason;} public ReturnStatus getStatus(){return status;} public Instant getCreatedAt(){return createdAt;}
 public void setShop(Shop v){shop=v;} public void setSale(Sale v){sale=v;} public void setRefundTotal(BigDecimal v){refundTotal=v;} public void setReason(String v){reason=v;} public void setStatus(ReturnStatus v){status=v;}
}
