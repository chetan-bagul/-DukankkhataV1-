package com.dukankhata.finance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal; import java.time.LocalDate; import java.util.*;
public interface FinancialExpenseRepository extends JpaRepository<FinancialExpense,UUID>{
 List<FinancialExpense> findByShopIdAndExpenseDateBetweenOrderByExpenseDateDescCreatedAtDesc(UUID shopId,LocalDate from,LocalDate to);
 Optional<FinancialExpense> findByIdAndShopId(UUID id,UUID shopId);
 long countByShopIdAndExpenseDateBetween(UUID shopId,LocalDate from,LocalDate to);
}
