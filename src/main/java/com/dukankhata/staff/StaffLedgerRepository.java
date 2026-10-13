package com.dukankhata.staff;
import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.math.BigDecimal; import java.time.Instant; import java.util.*;
public interface StaffLedgerRepository extends JpaRepository<StaffLedgerEntry,UUID>{
 List<StaffLedgerEntry> findByStaffIdOrderByCreatedAtDesc(UUID staffId);
 List<StaffLedgerEntry> findByStaffIdAndCreatedAtBetweenOrderByCreatedAtDesc(UUID staffId,Instant from,Instant to);
 @Query("select coalesce(sum(e.amount),0) from StaffLedgerEntry e where e.staff.id=:staffId") BigDecimal balance(UUID staffId);
 @Query("select e.staff.id, coalesce(sum(e.amount),0) from StaffLedgerEntry e where e.staff.shop.id=:shopId group by e.staff.id") List<Object[]> balancesByShopId(@Param("shopId") UUID shopId);
 boolean existsByReferenceIdAndType(UUID referenceId, StaffLedgerType type);
 @Query("select coalesce(sum(e.amount),0) from StaffLedgerEntry e where e.staff.shop.id=:shopId and e.type=:type and e.createdAt>=:from and e.createdAt<:to") BigDecimal findCommissionEarnedByShopIdBetween(@Param("shopId") UUID shopId,@Param("from") Instant from,@Param("to") Instant to,@Param("type") StaffLedgerType type);
}
