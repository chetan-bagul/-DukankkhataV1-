package com.dukankhata.purchase;
import com.dukankhata.product.Product; import jakarta.persistence.*; import java.math.BigDecimal; import java.util.UUID;
@Entity @Table(name="purchase_items", indexes={@Index(name="idx_purchase_item_purchase",columnList="purchase_id")})
public class PurchaseItem{
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="purchase_id",nullable=false) private Purchase purchase;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="product_id",nullable=false) private Product product;
 @Column(nullable=false,length=120) private String productName;
 @Column(nullable=false,precision=12,scale=3) private BigDecimal quantity;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal unitCost;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal lineTotal;
 public UUID getId(){return id;} public Product getProduct(){return product;} public String getProductName(){return productName;} public BigDecimal getQuantity(){return quantity;} public BigDecimal getUnitCost(){return unitCost;} public BigDecimal getLineTotal(){return lineTotal;}
 public void setPurchase(Purchase v){purchase=v;} public void setProduct(Product v){product=v;} public void setProductName(String v){productName=v;} public void setQuantity(BigDecimal v){quantity=v;} public void setUnitCost(BigDecimal v){unitCost=v;} public void setLineTotal(BigDecimal v){lineTotal=v;}
}
