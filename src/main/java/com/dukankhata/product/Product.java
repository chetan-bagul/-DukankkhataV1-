package com.dukankhata.product;

import com.dukankhata.shop.Shop;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_product_shop_active", columnList = "shop_id,active")
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal sellingPrice;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal averageCostPrice = BigDecimal.ZERO;

    @Column(name = "hsn_code", length = 8)
    private String hsnCode;

    @Column(name = "gst_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal gstRate = BigDecimal.ZERO;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal currentStock = BigDecimal.ZERO;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal reorderLevel = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() { updatedAt = Instant.now(); }

    public UUID getId() { return id; }
    public Shop getShop() { return shop; }
    public String getName() { return name; }
    public BigDecimal getSellingPrice() { return sellingPrice; }
    public BigDecimal getAverageCostPrice() { return averageCostPrice; }
    public String getHsnCode() { return hsnCode; }
    public BigDecimal getGstRate() { return gstRate; }
    public BigDecimal getCurrentStock() { return currentStock; }
    public BigDecimal getReorderLevel() { return reorderLevel; }
    public boolean isActive() { return active; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setShop(Shop shop) { this.shop = shop; }
    public void setName(String name) { this.name = name; }
    public void setSellingPrice(BigDecimal sellingPrice) { this.sellingPrice = sellingPrice; }
    public void setAverageCostPrice(BigDecimal averageCostPrice) { this.averageCostPrice = averageCostPrice; }
    public void setHsnCode(String v) { this.hsnCode = v; }
    public void setGstRate(BigDecimal v) { this.gstRate = v == null ? BigDecimal.ZERO : v; }
    public void setCurrentStock(BigDecimal currentStock) { this.currentStock = currentStock; }
    public void setReorderLevel(BigDecimal reorderLevel) { this.reorderLevel = reorderLevel; }
    public void setActive(boolean active) { this.active = active; }
}
