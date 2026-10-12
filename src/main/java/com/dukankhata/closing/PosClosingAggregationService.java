package com.dukankhata.closing;

import com.dukankhata.common.security.CurrentUserService;
import com.dukankhata.pos.PaymentMethod;
import com.dukankhata.pos.SaleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.util.UUID;

@Service
public class PosClosingAggregationService {
    private final SaleRepository saleRepository;
    private final CurrentUserService currentUserService;
    private final ZoneId businessZone;

    public PosClosingAggregationService(SaleRepository saleRepository,
                                        CurrentUserService currentUserService,
                                        @Value("${app.business-time-zone:Asia/Kolkata}") String timeZone) {
        this.saleRepository = saleRepository;
        this.currentUserService = currentUserService;
        this.businessZone = ZoneId.of(timeZone);
    }

    public PosTotals aggregate(LocalDate businessDate) {
        UUID shopId = currentUserService.shopId();
        Instant from = businessDate.atStartOfDay(businessZone).toInstant();
        Instant to = businessDate.plusDays(1).atStartOfDay(businessZone).toInstant();
        BigDecimal total = money(saleRepository.sumTotal(shopId, from, to));
        BigDecimal cash = money(saleRepository.sumPayment(shopId, PaymentMethod.CASH, from, to));
        BigDecimal upi = money(saleRepository.sumPayment(shopId, PaymentMethod.UPI, from, to));
        BigDecimal udhar = money(saleRepository.sumPayment(shopId, PaymentMethod.UDHAR, from, to));
        long count = saleRepository.countCompletedByShopIdAndCreatedAtBetween(shopId, from, to);
        return new PosTotals(total, cash, upi, udhar, count);
    }

    private BigDecimal money(BigDecimal value) {
        return (value == null ? BigDecimal.ZERO : value).setScale(2, java.math.RoundingMode.HALF_UP);
    }

    public record PosTotals(BigDecimal total, BigDecimal cash, BigDecimal upi, BigDecimal udhar, long saleCount) {}
}
