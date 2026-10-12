package com.dukankhata.notification.automation;

import com.dukankhata.notification.NotificationType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

@Service
public class NotificationTemplateService {
    public String subject(NotificationType type) {
        return switch (type) {
            case INVOICE -> "DukanKhata Invoice";
            case PAYMENT_RECEIPT -> "DukanKhata Payment Receipt";
            case UDHAR_REMINDER -> "DukanKhata Udhar Reminder";
            case LOW_STOCK -> "DukanKhata Low Stock Alert";
            case SUPPLIER_PAYMENT -> "DukanKhata Supplier Payment Receipt";
            case SUBSCRIPTION -> "DukanKhata Subscription Update";
        };
    }

    public String render(NotificationType type, Map<String, String> values) {
        return (switch (type) {
            case INVOICE -> "Hello {{customerName}}, your invoice {{invoiceNumber}} from {{shopName}} is {{amount}}. Thank you for shopping with us.";
            case PAYMENT_RECEIPT -> "Hello {{customerName}}, payment of {{amount}} has been recorded by {{shopName}}. Thank you.";
            case UDHAR_REMINDER -> "Hello {{customerName}}, your current Udhar balance with {{shopName}} is {{balance}}. Please contact the shop if you have already paid.";
            case LOW_STOCK -> "Low stock alert at {{shopName}}: {{productName}} has {{stock}} units remaining. Reorder level is {{reorderLevel}}.";
            case SUPPLIER_PAYMENT -> "Payment of {{amount}} has been recorded for supplier {{supplierName}} by {{shopName}}. Remaining payable: {{balance}}.";
            case SUBSCRIPTION -> "{{message}}";
        }).replace("{{customerName}}", values.getOrDefault("customerName", ""))
             .replace("{{invoiceNumber}}", values.getOrDefault("invoiceNumber", ""))
             .replace("{{shopName}}", values.getOrDefault("shopName", ""))
             .replace("{{amount}}", values.getOrDefault("amount", ""))
             .replace("{{balance}}", values.getOrDefault("balance", ""))
             .replace("{{productName}}", values.getOrDefault("productName", ""))
             .replace("{{stock}}", values.getOrDefault("stock", ""))
             .replace("{{reorderLevel}}", values.getOrDefault("reorderLevel", ""))
             .replace("{{supplierName}}", values.getOrDefault("supplierName", ""))
             .replace("{{message}}", values.getOrDefault("message", ""));
    }

    public String money(BigDecimal value) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(value == null ? BigDecimal.ZERO : value);
    }
}
