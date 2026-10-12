package com.dukankhata.auth;

import com.dukankhata.shop.Shop;
import com.dukankhata.staff.Staff;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_mobile", columnNames = "mobile"),
        @UniqueConstraint(name = "uk_user_staff", columnNames = "staff_id")
})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(nullable = false, length = 10) private String mobile;
    @Column(nullable = false, length = 100) private String name;
    @Column(nullable = false, length = 100) private String passwordHash;
    @Column(nullable = false, length = 30) private String role = "OWNER";
    @Column(nullable = false) private boolean active = true;
    @ManyToOne(fetch = FetchType.EAGER, optional = false) @JoinColumn(name = "shop_id", nullable = false) private Shop shop;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "staff_id") private Staff staff;
    @Column(nullable = false, updatable = false) private Instant createdAt;
    @PrePersist void onCreate() { createdAt = Instant.now(); }
    public UUID getId(){return id;} public String getMobile(){return mobile;} public String getName(){return name;} public String getPasswordHash(){return passwordHash;} public String getRole(){return role;} public boolean isActive(){return active;} public Shop getShop(){return shop;} public Staff getStaff(){return staff;} public Instant getCreatedAt(){return createdAt;}
    public void setMobile(String v){mobile=v;} public void setName(String v){name=v;} public void setPasswordHash(String v){passwordHash=v;} public void setRole(String v){role=v;} public void setActive(boolean v){active=v;} public void setShop(Shop v){shop=v;} public void setStaff(Staff v){staff=v;}
}
