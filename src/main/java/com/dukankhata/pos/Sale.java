package com.dukankhata.pos;
import com.dukankhata.customer.Customer;
import com.dukankhata.shop.Shop; import com.dukankhata.staff.Staff;
import jakarta.persistence.*;
import java.math.BigDecimal; import java.time.Instant; import java.util.*;
@Entity @Table(name="pos_sales", indexes={@Index(name="idx_pos_sale_shop_created",columnList="shop_id,created_at"),@Index(name="idx_pos_sale_shop_invoice",columnList="shop_id,invoice_number")}, uniqueConstraints=@UniqueConstraint(name="uk_pos_sale_shop_invoice",columnNames={"shop_id","invoice_number"}))
public class Sale {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="shop_id",nullable=false) private Shop shop;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="customer_id") private Customer customer;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="seller_id") private Staff seller;
 @Column(name="invoice_number",nullable=false,length=40) private String invoiceNumber;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal subtotal=BigDecimal.ZERO;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal tax=BigDecimal.ZERO;
 @Column(name="taxable_amount",nullable=false,precision=14,scale=2) private BigDecimal taxableAmount=BigDecimal.ZERO;
 @Column(name="cgst_amount",nullable=false,precision=14,scale=2) private BigDecimal cgstAmount=BigDecimal.ZERO;
 @Column(name="sgst_amount",nullable=false,precision=14,scale=2) private BigDecimal sgstAmount=BigDecimal.ZERO;
 @Column(name="igst_amount",nullable=false,precision=14,scale=2) private BigDecimal igstAmount=BigDecimal.ZERO;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal total=BigDecimal.ZERO;
 @Column(name="seller_commission_rate",nullable=false,precision=5,scale=2) private BigDecimal sellerCommissionRate=BigDecimal.ZERO;
 @Column(name="seller_commission_amount",nullable=false,precision=14,scale=2) private BigDecimal sellerCommissionAmount=BigDecimal.ZERO;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private SaleStatus status=SaleStatus.COMPLETED;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @OneToMany(mappedBy="sale",cascade=CascadeType.ALL,orphanRemoval=true) private List<SaleItem> items=new ArrayList<>();
 @OneToMany(mappedBy="sale",cascade=CascadeType.ALL,orphanRemoval=true) private List<SalePayment> payments=new ArrayList<>();
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public Customer getCustomer(){return customer;} public Staff getSeller(){return seller;} public String getInvoiceNumber(){return invoiceNumber;} public BigDecimal getSubtotal(){return subtotal;} public BigDecimal getTax(){return tax;} public BigDecimal getTaxableAmount(){return taxableAmount;} public BigDecimal getCgstAmount(){return cgstAmount;} public BigDecimal getSgstAmount(){return sgstAmount;} public BigDecimal getIgstAmount(){return igstAmount;} public BigDecimal getTotal(){return total;} public BigDecimal getSellerCommissionRate(){return sellerCommissionRate;} public BigDecimal getSellerCommissionAmount(){return sellerCommissionAmount;} public SaleStatus getStatus(){return status;} public Instant getCreatedAt(){return createdAt;} public List<SaleItem> getItems(){return items;} public List<SalePayment> getPayments(){return payments;}
 public void setShop(Shop v){shop=v;} public void setCustomer(Customer v){customer=v;} public void setSeller(Staff v){seller=v;} public void setInvoiceNumber(String v){invoiceNumber=v;} public void setSubtotal(BigDecimal v){subtotal=v;} public void setTax(BigDecimal v){tax=v;} public void setTaxableAmount(BigDecimal v){taxableAmount=v;} public void setCgstAmount(BigDecimal v){cgstAmount=v;} public void setSgstAmount(BigDecimal v){sgstAmount=v;} public void setIgstAmount(BigDecimal v){igstAmount=v;} public void setTotal(BigDecimal v){total=v;} public void setSellerCommissionRate(BigDecimal v){sellerCommissionRate=v;} public void setSellerCommissionAmount(BigDecimal v){sellerCommissionAmount=v;} public void setStatus(SaleStatus v){status=v;}
}
