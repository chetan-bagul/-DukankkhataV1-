package com.dukankhata.pos;
import jakarta.persistence.*; import java.math.BigDecimal; import java.util.UUID;
@Entity @Table(name="pos_sale_payments") public class SalePayment {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="sale_id",nullable=false) private Sale sale;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private PaymentMethod method;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal amount;
 public UUID getId(){return id;} public Sale getSale(){return sale;} public PaymentMethod getMethod(){return method;} public BigDecimal getAmount(){return amount;}
 public void setSale(Sale v){sale=v;} public void setMethod(PaymentMethod v){method=v;} public void setAmount(BigDecimal v){amount=v;}
}
