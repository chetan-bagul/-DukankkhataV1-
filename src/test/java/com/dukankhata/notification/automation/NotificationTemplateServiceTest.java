package com.dukankhata.notification.automation;

import com.dukankhata.notification.NotificationType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTemplateServiceTest {
    private final NotificationTemplateService service = new NotificationTemplateService();

    @Test
    void rendersInvoiceTemplate() {
        String message = service.render(NotificationType.INVOICE, Map.of(
                "customerName", "Rahul",
                "invoiceNumber", "INV-20261008-0012",
                "shopName", "My Shop",
                "amount", "₹1,180.00"
        ));

        assertEquals("Hello Rahul, your invoice INV-20261008-0012 from My Shop is ₹1,180.00. Thank you for shopping with us.", message);
    }

    @Test
    void rendersLowStockTemplate() {
        String message = service.render(NotificationType.LOW_STOCK, Map.of(
                "shopName", "My Shop",
                "productName", "Basmati Rice",
                "stock", "5",
                "reorderLevel", "5"
        ));

        assertTrue(message.contains("Basmati Rice"));
        assertTrue(message.contains("5 units remaining"));
        assertTrue(message.contains("Reorder level is 5"));
    }

    @Test
    void moneyUsesIndianCurrencyFormatting() {
        String formatted = service.money(new BigDecimal("1234.50"));
        assertTrue(formatted.contains("1,234.50"), formatted);
    }
}
