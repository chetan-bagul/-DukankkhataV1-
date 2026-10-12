package com.dukankhata.customer;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findAllByShopIdAndActiveTrueOrderByCustomerNumberAsc(UUID shopId);
    Optional<Customer> findByIdAndShopIdAndActiveTrue(UUID id, UUID shopId);
    boolean existsByShopIdAndNameIgnoreCaseAndActiveTrue(UUID shopId, String name);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Customer c where c.id = :id and c.shop.id = :shopId and c.active = true")
    Optional<Customer> findByIdAndShopIdForUpdate(@Param("id") UUID id, @Param("shopId") UUID shopId);
}
