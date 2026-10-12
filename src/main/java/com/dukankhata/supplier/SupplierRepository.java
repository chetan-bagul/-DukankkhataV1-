package com.dukankhata.supplier;
import jakarta.persistence.LockModeType; import org.springframework.data.jpa.repository.*; import java.util.*;
public interface SupplierRepository extends JpaRepository<Supplier,UUID>{
 List<Supplier> findAllByShopIdAndActiveTrueOrderByNameAsc(UUID shopId);
 Optional<Supplier> findByIdAndShopIdAndActiveTrue(UUID id,UUID shopId);
 boolean existsByShopIdAndNameIgnoreCaseAndActiveTrue(UUID shopId,String name);
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select s from Supplier s where s.id=:id and s.shop.id=:shopId and s.active=true") Optional<Supplier> findByIdAndShopIdForUpdate(UUID id,UUID shopId);
}
