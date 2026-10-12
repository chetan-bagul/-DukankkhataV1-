package com.dukankhata.inventory;
import com.dukankhata.product.Product; import com.dukankhata.shop.Shop; import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="stock_movements", indexes={@Index(name="idx_stock_movement_shop_created",columnList="shop_id,created_at"),@Index(name="idx_stock_movement_product_created",columnList="product_id,created_at")})
public class StockMovement {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="shop_id",nullable=false) private Shop shop;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="product_id",nullable=false) private Product product;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private StockMovementType type;
 @Column(nullable=false,precision=12,scale=3) private BigDecimal quantity;
 @Column(nullable=false,precision=12,scale=3) private BigDecimal balanceAfter;
 @Column(length=300) private String note;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public Product getProduct(){return product;} public StockMovementType getType(){return type;} public BigDecimal getQuantity(){return quantity;} public BigDecimal getBalanceAfter(){return balanceAfter;} public String getNote(){return note;} public Instant getCreatedAt(){return createdAt;}
 public void setShop(Shop v){shop=v;} public void setProduct(Product v){product=v;} public void setType(StockMovementType v){type=v;} public void setQuantity(BigDecimal v){quantity=v;} public void setBalanceAfter(BigDecimal v){balanceAfter=v;} public void setNote(String v){note=v;}
}
