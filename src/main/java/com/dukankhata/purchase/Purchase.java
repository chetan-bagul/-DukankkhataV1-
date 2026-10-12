package com.dukankhata.purchase;
import com.dukankhata.shop.Shop; import com.dukankhata.supplier.Supplier; import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.util.*;
@Entity @Table(name="purchases", indexes={@Index(name="idx_purchase_shop_created",columnList="shop_id,created_at"),@Index(name="idx_purchase_supplier_created",columnList="supplier_id,created_at")})
public class Purchase{
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="shop_id",nullable=false) private Shop shop;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="supplier_id",nullable=false) private Supplier supplier;
 @Column(nullable=false,length=50) private String purchaseNumber;
 @Column(length=80) private String supplierInvoiceNumber;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal subtotal=BigDecimal.ZERO;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal tax=BigDecimal.ZERO;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal total=BigDecimal.ZERO;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal paidAmount=BigDecimal.ZERO;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal payableAmount=BigDecimal.ZERO;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private PurchasePaymentStatus paymentStatus;
 @Column(length=500) private String note;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @OneToMany(mappedBy="purchase",cascade=CascadeType.ALL,orphanRemoval=true) private List<PurchaseItem> items=new ArrayList<>();
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public Supplier getSupplier(){return supplier;} public String getPurchaseNumber(){return purchaseNumber;} public String getSupplierInvoiceNumber(){return supplierInvoiceNumber;} public BigDecimal getSubtotal(){return subtotal;} public BigDecimal getTax(){return tax;} public BigDecimal getTotal(){return total;} public BigDecimal getPaidAmount(){return paidAmount;} public BigDecimal getPayableAmount(){return payableAmount;} public PurchasePaymentStatus getPaymentStatus(){return paymentStatus;} public String getNote(){return note;} public Instant getCreatedAt(){return createdAt;} public List<PurchaseItem> getItems(){return items;}
 public void setShop(Shop v){shop=v;} public void setSupplier(Supplier v){supplier=v;} public void setPurchaseNumber(String v){purchaseNumber=v;} public void setSupplierInvoiceNumber(String v){supplierInvoiceNumber=v;} public void setSubtotal(BigDecimal v){subtotal=v;} public void setTax(BigDecimal v){tax=v;} public void setTotal(BigDecimal v){total=v;} public void setPaidAmount(BigDecimal v){paidAmount=v;} public void setPayableAmount(BigDecimal v){payableAmount=v;} public void setPaymentStatus(PurchasePaymentStatus v){paymentStatus=v;} public void setNote(String v){note=v;}
}
