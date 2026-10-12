package com.dukankhata.returns;
import jakarta.persistence.*; import java.math.BigDecimal; import java.util.UUID;
@Entity @Table(name="sale_return_refunds") public class ReturnRefund {
 @Id @GeneratedValue(strategy=GenerationType.UUID) UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="return_id",nullable=false) SaleReturn saleReturn;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) ReturnRefundMethod method;
 @Column(nullable=false,precision=14,scale=2) BigDecimal amount;
 public UUID getId(){return id;} public SaleReturn getSaleReturn(){return saleReturn;} public ReturnRefundMethod getMethod(){return method;} public BigDecimal getAmount(){return amount;}
 public void setSaleReturn(SaleReturn v){saleReturn=v;} public void setMethod(ReturnRefundMethod v){method=v;} public void setAmount(BigDecimal v){amount=v;}
}
