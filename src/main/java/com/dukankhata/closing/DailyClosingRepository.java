package com.dukankhata.closing; import org.springframework.data.jpa.repository.JpaRepository; import java.time.LocalDate; import java.util.*;
public interface DailyClosingRepository extends JpaRepository<DailyClosing,UUID>{Optional<DailyClosing> findByIdAndShopId(UUID id,UUID shopId);Optional<DailyClosing> findByShopIdAndBusinessDate(UUID shopId,LocalDate businessDate);List<DailyClosing> findTop30ByShopIdOrderByBusinessDateDesc(UUID shopId);
List<DailyClosing> findByShopIdAndBusinessDateBetweenAndStatusOrderByBusinessDateAsc(UUID shopId, LocalDate from, LocalDate to, ClosingStatus status);}
