package com.dukankhata.staff;
import jakarta.persistence.*; import java.math.BigDecimal; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="staff_ledger_entries", indexes={@Index(name="idx_staff_ledger_staff_created",columnList="staff_id,created_at")})
public class StaffLedgerEntry {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="staff_id",nullable=false) private Staff staff;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=30) private StaffLedgerType type;
 @Column(nullable=false,precision=14,scale=2) private BigDecimal amount;
 @Column(nullable=false,length=500) private String description;
 @Column(name="reference_id") private UUID referenceId;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @PrePersist void create(){createdAt=Instant.now();}
 public UUID getId(){return id;} public Staff getStaff(){return staff;} public StaffLedgerType getType(){return type;} public BigDecimal getAmount(){return amount;} public String getDescription(){return description;} public UUID getReferenceId(){return referenceId;} public Instant getCreatedAt(){return createdAt;}
 public void setStaff(Staff v){staff=v;} public void setType(StaffLedgerType v){type=v;} public void setAmount(BigDecimal v){amount=v;} public void setDescription(String v){description=v;} public void setReferenceId(UUID v){referenceId=v;}
}
