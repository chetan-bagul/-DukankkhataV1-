package com.dukankhata.supplier;
import com.dukankhata.shop.Shop; import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="supplier_ledger_entries", indexes={@Index(name="idx_supplier_ledger_supplier_created",columnList="supplier_id,created_at")})
public class SupplierLedgerEntry{
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="shop_id",nullable=false) private Shop shop;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="supplier_id",nullable=false) private Supplier supplier;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private SupplierLedgerType type;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal amount;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal balanceAfter;
 @Column(length=300) private String reference;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Supplier getSupplier(){return supplier;} public SupplierLedgerType getType(){return type;} public BigDecimal getAmount(){return amount;} public BigDecimal getBalanceAfter(){return balanceAfter;} public String getReference(){return reference;} public Instant getCreatedAt(){return createdAt;}
 public void setShop(Shop v){shop=v;} public void setSupplier(Supplier v){supplier=v;} public void setType(SupplierLedgerType v){type=v;} public void setAmount(BigDecimal v){amount=v;} public void setBalanceAfter(BigDecimal v){balanceAfter=v;} public void setReference(String v){reference=v;}
}
