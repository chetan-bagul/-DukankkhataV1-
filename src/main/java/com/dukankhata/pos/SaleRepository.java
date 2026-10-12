package com.dukankhata.pos;
import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.math.BigDecimal; import java.time.Instant; import java.util.*;
public interface SaleRepository extends JpaRepository<Sale,UUID> {
 Optional<Sale> findByIdAndShopId(UUID id, UUID shopId);
 List<Sale> findAllByShopIdOrderByCreatedAtDesc(UUID shopId);
 @Query("select distinct s from Sale s left join fetch s.items where s.shop.id=:shopId and s.status='COMPLETED' and s.createdAt>=:from and s.createdAt<:to") List<Sale> findCompletedByShopIdAndCreatedAtBetween(@Param("shopId") UUID shopId,@Param("from") Instant from,@Param("to") Instant to);
 @Query("select coalesce(sum(s.total),0) from Sale s where s.shop.id=:shopId and s.status='COMPLETED' and s.createdAt>=:from and s.createdAt<:to") BigDecimal sumTotal(@Param("shopId") UUID shopId,@Param("from") Instant from,@Param("to") Instant to);
 @Query("select coalesce(sum(p.amount),0) from SalePayment p where p.sale.shop.id=:shopId and p.sale.status='COMPLETED' and p.method=:method and p.sale.createdAt>=:from and p.sale.createdAt<:to") BigDecimal sumPayment(@Param("shopId") UUID shopId,@Param("method") PaymentMethod method,@Param("from") Instant from,@Param("to") Instant to);
 long countByShopIdAndCreatedAtBetween(UUID shopId, Instant from, Instant to);
 @Query("select count(s) from Sale s where s.shop.id=:shopId and s.status='COMPLETED' and s.createdAt>=:from and s.createdAt<:to") long countCompletedByShopIdAndCreatedAtBetween(@Param("shopId") UUID shopId,@Param("from") Instant from,@Param("to") Instant to);
}
