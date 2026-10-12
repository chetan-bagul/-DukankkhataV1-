package com.dukankhata.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface CustomerLedgerRepository extends JpaRepository<CustomerLedgerEntry, UUID> {
    List<CustomerLedgerEntry> findAllByCustomerIdAndShopIdOrderByCreatedAtDesc(UUID customerId, UUID shopId);
}
