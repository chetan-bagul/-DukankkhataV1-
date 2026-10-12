package com.dukankhata.pos;
import com.dukankhata.product.Product; import jakarta.persistence.*; import java.math.BigDecimal; import java.util.UUID;
@Entity @Table(name="pos_sale_items") public class SaleItem {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="sale_id",nullable=false) private Sale sale;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="product_id") private Product product;
 @Column(nullable=false,length=160) private String itemName;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal unitPrice;
 @Column(nullable=false,precision=12,scale=3) private BigDecimal quantity;
 @Column(nullable=false,precision=12,scale=2,columnDefinition="numeric(12,2) default 0") private BigDecimal costPrice=BigDecimal.ZERO;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal lineTotal;
 @Column(name="hsn_code",length=8) private String hsnCode;
 @Column(name="gst_rate",nullable=false,precision=5,scale=2) private BigDecimal gstRate=BigDecimal.ZERO;
 @Column(name="taxable_amount",nullable=false,precision=14,scale=2) private BigDecimal taxableAmount=BigDecimal.ZERO;
 @Column(name="cgst_amount",nullable=false,precision=14,scale=2) private BigDecimal cgstAmount=BigDecimal.ZERO;
 @Column(name="sgst_amount",nullable=false,precision=14,scale=2) private BigDecimal sgstAmount=BigDecimal.ZERO;
 @Column(name="igst_amount",nullable=false,precision=14,scale=2) private BigDecimal igstAmount=BigDecimal.ZERO;
 public UUID getId(){return id;} public Sale getSale(){return sale;} public Product getProduct(){return product;} public String getItemName(){return itemName;} public BigDecimal getUnitPrice(){return unitPrice;} public BigDecimal getQuantity(){return quantity;} public BigDecimal getCostPrice(){return costPrice;} public BigDecimal getLineTotal(){return lineTotal;} public String getHsnCode(){return hsnCode;} public BigDecimal getGstRate(){return gstRate;} public BigDecimal getTaxableAmount(){return taxableAmount;} public BigDecimal getCgstAmount(){return cgstAmount;} public BigDecimal getSgstAmount(){return sgstAmount;} public BigDecimal getIgstAmount(){return igstAmount;}
 public void setSale(Sale v){sale=v;} public void setProduct(Product v){product=v;} public void setItemName(String v){itemName=v;} public void setUnitPrice(BigDecimal v){unitPrice=v;} public void setQuantity(BigDecimal v){quantity=v;} public void setCostPrice(BigDecimal v){costPrice=v;} public void setLineTotal(BigDecimal v){lineTotal=v;} public void setHsnCode(String v){hsnCode=v;} public void setGstRate(BigDecimal v){gstRate=v==null?BigDecimal.ZERO:v;} public void setTaxableAmount(BigDecimal v){taxableAmount=v;} public void setCgstAmount(BigDecimal v){cgstAmount=v;} public void setSgstAmount(BigDecimal v){sgstAmount=v;} public void setIgstAmount(BigDecimal v){igstAmount=v;}
}
