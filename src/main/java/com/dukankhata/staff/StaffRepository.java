package com.dukankhata.staff;
import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import jakarta.persistence.LockModeType; import java.util.*;
public interface StaffRepository extends JpaRepository<Staff,UUID>{
 List<Staff> findByShopIdOrderByActiveDescNameAsc(UUID shopId);
 Optional<Staff> findByIdAndShopId(UUID id,UUID shopId);
 Optional<Staff> findByIdAndShopIdAndActiveTrue(UUID id,UUID shopId);
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select s from Staff s where s.id=:id and s.shop.id=:shopId and s.active=true") Optional<Staff> findForUpdate(@Param("id") UUID id,@Param("shopId") UUID shopId);
}
