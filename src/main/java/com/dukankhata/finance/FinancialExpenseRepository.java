package com.dukankhata.finance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.*;
public interface FinancialExpenseRepository extends JpaRepository<FinancialExpense,UUID>{
 List<FinancialExpense> findByShopIdAndExpenseDateBetweenOrderByExpenseDateDescCreatedAtDesc(UUID shopId,LocalDate from,LocalDate to);
 @Query("select coalesce(sum(e.amount),0) from FinancialExpense e where e.shop.id=:shopId and e.expenseDate between :from and :to") BigDecimal sumByShopAndDateRange(@Param("shopId") UUID shopId,@Param("from") LocalDate from,@Param("to") LocalDate to);
 Optional<FinancialExpense> findByIdAndShopId(UUID id,UUID shopId);
 long countByShopIdAndExpenseDateBetween(UUID shopId,LocalDate from,LocalDate to);
}
