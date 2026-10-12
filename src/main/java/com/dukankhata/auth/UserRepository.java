package com.dukankhata.auth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface UserRepository extends JpaRepository<User, UUID> {
 Optional<User> findByMobile(String mobile);
 boolean existsByMobile(String mobile);
 boolean existsByStaffId(UUID staffId);
 List<User> findByShopIdOrderByCreatedAtDesc(UUID shopId);
}
