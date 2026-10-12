package com.dukankhata.notification.automation;

import com.dukankhata.auth.User;
import com.dukankhata.auth.UserRepository;
import com.dukankhata.customer.Customer;
import com.dukankhata.notification.*;
import com.dukankhata.shop.Shop;
import com.dukankhata.supplier.Supplier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class AutomatedNotificationService {
    private final NotificationService notifications;
    private final NotificationTemplateService templates;
    private final UserRepository users;

    public AutomatedNotificationService(NotificationService notifications, NotificationTemplateService templates, UserRepository users) {
        this.notifications = notifications;
        this.templates = templates;
        this.users = users;
    }

    public void invoice(Shop shop, Customer customer, String invoiceNumber, BigDecimal amount) {
        if (!hasMobile(customer)) return;
        Map<String, String> v = values("customerName", customer.getName(), "shopName", shop.getName(),
                "invoiceNumber", invoiceNumber, "amount", templates.money(amount));
        notifications.automated(shop, NotificationChannel.WHATSAPP, NotificationType.INVOICE,
                customer.getMobile(), templates.subject(NotificationType.INVOICE), templates.render(NotificationType.INVOICE, v));
    }

    public void customerPayment(Shop shop, Customer customer, BigDecimal amount) {
        if (!hasMobile(customer)) return;
        Map<String, String> v = values("customerName", customer.getName(), "shopName", shop.getName(),
                "amount", templates.money(amount));
        notifications.automated(shop, NotificationChannel.WHATSAPP, NotificationType.PAYMENT_RECEIPT,
                customer.getMobile(), templates.subject(NotificationType.PAYMENT_RECEIPT), templates.render(NotificationType.PAYMENT_RECEIPT, v));
    }

    public void udharReminder(Shop shop, Customer customer) {
        if (!hasMobile(customer)) return;
        Map<String, String> v = values("customerName", customer.getName(), "shopName", shop.getName(),
                "balance", templates.money(customer.getBalance().negate()));
        notifications.automated(shop, NotificationChannel.WHATSAPP, NotificationType.UDHAR_REMINDER,
                customer.getMobile(), templates.subject(NotificationType.UDHAR_REMINDER), templates.render(NotificationType.UDHAR_REMINDER, v));
    }

    public void lowStock(Shop shop, com.dukankhata.product.Product product) {
        if (shop == null || product == null) return;
        String recipient = users.findByShopIdOrderByCreatedAtDesc(shop.getId()).stream()
                .filter(u -> "OWNER".equals(u.getRole()) && u.isActive())
                .map(User::getMobile).filter(m -> m != null && !m.isBlank()).findFirst().orElse(null);
        if (recipient == null || recipient.isBlank()) return;
        Map<String, String> v = values("shopName", shop.getName(), "productName", product.getName(),
                "stock", product.getCurrentStock().toPlainString(), "reorderLevel", product.getReorderLevel().toPlainString());
        notifications.automated(shop, NotificationChannel.WHATSAPP, NotificationType.LOW_STOCK,
                recipient, templates.subject(NotificationType.LOW_STOCK), templates.render(NotificationType.LOW_STOCK, v));
    }

    public void supplierPayment(Shop shop, Supplier supplier, BigDecimal amount) {
        if (supplier == null || supplier.getMobile() == null || supplier.getMobile().isBlank()) return;
        Map<String, String> v = values("shopName", shop.getName(), "supplierName", supplier.getName(),
                "amount", templates.money(amount), "balance", templates.money(supplier.getBalancePayable()));
        notifications.automated(shop, NotificationChannel.WHATSAPP, NotificationType.SUPPLIER_PAYMENT,
                supplier.getMobile(), templates.subject(NotificationType.SUPPLIER_PAYMENT), templates.render(NotificationType.SUPPLIER_PAYMENT, v));
    }

    private boolean hasMobile(Customer c) { return c != null && c.getMobile() != null && !c.getMobile().isBlank(); }

    private Map<String, String> values(String... entries) {
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < entries.length; i += 2) map.put(entries[i], entries[i + 1]);
        return map;
    }
}
