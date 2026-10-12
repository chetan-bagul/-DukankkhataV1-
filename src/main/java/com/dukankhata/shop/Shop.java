package com.dukankhata.shop;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 20)
    private String operatingMode = "BOTH";

    @Column(nullable = false, length = 30)
    private String planStatus = "TRIAL";

    @Column(name = "gstin", length = 15)
    private String gstin;

    @Column(name = "legal_name", length = 160)
    private String legalName;

    @Column(name = "address_line", length = 300)
    private String addressLine;

    @Column(name = "city", length = 80)
    private String city;

    @Column(name = "state", length = 80)
    private String state;

    @Column(name = "state_code", length = 2)
    private String stateCode;

    @Column(name = "pincode", length = 6)
    private String pincode;

    @Column(nullable = false)
    private Integer nextCustomerNumber = 1;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() { createdAt = Instant.now(); }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getOperatingMode() { return operatingMode; }
    public String getPlanStatus() { return planStatus; }
    public String getGstin() { return gstin; }
    public String getLegalName() { return legalName; }
    public String getAddressLine() { return addressLine; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getStateCode() { return stateCode; }
    public String getPincode() { return pincode; }
    public Instant getCreatedAt() { return createdAt; }
    public Integer getNextCustomerNumber() { return nextCustomerNumber; }

    public void setName(String name) { this.name = name; }
    public void setOperatingMode(String operatingMode) { this.operatingMode = operatingMode; }
    public void setPlanStatus(String planStatus) { this.planStatus = planStatus; }
    public void setGstin(String v) { this.gstin = v; }
    public void setLegalName(String v) { this.legalName = v; }
    public void setAddressLine(String v) { this.addressLine = v; }
    public void setCity(String v) { this.city = v; }
    public void setState(String v) { this.state = v; }
    public void setStateCode(String v) { this.stateCode = v; }
    public void setPincode(String v) { this.pincode = v; }
    public void setNextCustomerNumber(Integer nextCustomerNumber) { this.nextCustomerNumber = nextCustomerNumber; }
}
