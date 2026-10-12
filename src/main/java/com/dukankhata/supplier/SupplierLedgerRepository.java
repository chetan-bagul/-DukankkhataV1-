package com.dukankhata.supplier;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SupplierLedgerRepository extends JpaRepository<SupplierLedgerEntry,UUID>{ List<SupplierLedgerEntry> findTop100BySupplierIdAndShopIdOrderByCreatedAtDesc(UUID supplierId,UUID shopId); }
