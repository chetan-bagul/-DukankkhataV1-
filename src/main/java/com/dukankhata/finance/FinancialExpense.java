package com.dukankhata.finance;
import com.dukankhata.shop.Shop;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="financial_expenses", indexes={@Index(name="idx_fin_exp_shop_date",columnList="shop_id,expense_date")})
public class FinancialExpense {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="shop_id",nullable=false) private Shop shop;
 @Column(name="expense_date",nullable=false) private LocalDate expenseDate;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private ExpenseCategory category;
 @Column(nullable=false,length=180) private String description;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal amount;
 @Column(nullable=false) private boolean recurringMonthly=false;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Shop getShop(){return shop;} public LocalDate getExpenseDate(){return expenseDate;} public ExpenseCategory getCategory(){return category;} public String getDescription(){return description;} public BigDecimal getAmount(){return amount;} public boolean isRecurringMonthly(){return recurringMonthly;} public Instant getCreatedAt(){return createdAt;}
 public void setShop(Shop v){shop=v;} public void setExpenseDate(LocalDate v){expenseDate=v;} public void setCategory(ExpenseCategory v){category=v;} public void setDescription(String v){description=v;} public void setAmount(BigDecimal v){amount=v;} public void setRecurringMonthly(boolean v){recurringMonthly=v;}
}
