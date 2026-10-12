package com.dukankhata.returns;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SaleReturnRepository extends JpaRepository<SaleReturn,UUID>{ Optional<SaleReturn> findBySaleIdAndShopId(UUID saleId,UUID shopId); List<SaleReturn> findTop100ByShopIdOrderByCreatedAtDesc(UUID shopId); }
