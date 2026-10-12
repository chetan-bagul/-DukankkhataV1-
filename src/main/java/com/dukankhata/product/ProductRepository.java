package com.dukankhata.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByShopIdAndActiveTrueOrderByNameAsc(UUID shopId);
    Optional<Product> findByIdAndShopId(UUID id, UUID shopId);
    Optional<Product> findByIdAndShopIdAndActiveTrue(UUID id, UUID shopId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id=:id and p.shop.id=:shopId")
    Optional<Product> findByIdAndShopIdForUpdate(@Param("id") UUID id, @Param("shopId") UUID shopId);
    boolean existsByShopIdAndNameIgnoreCaseAndActiveTrue(UUID shopId, String name);
    boolean existsByShopIdAndNameIgnoreCaseAndActiveTrueAndIdNot(UUID shopId, String name, UUID id);
}
