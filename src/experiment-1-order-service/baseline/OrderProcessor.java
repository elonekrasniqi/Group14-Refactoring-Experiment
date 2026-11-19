package baseline;

import java.util.Locale;

public class OrderProcessor {
    public String processOrder(String customer, double price,
                               int quantity, boolean discount) {
        String name = customer.trim().toUpperCase(Locale.ROOT);
        double total = price * quantity;
        if (discount) {
            total = total - total * 0.10;
        }
        double tax = total * 0.18;
        total = total + tax;
        return name + ": " + String.format(Locale.ROOT, "%.2f", total);
    }

    public String processRefund(String customer, double amount) {
        String name = customer.trim().toUpperCase(Locale.ROOT);
        return name + ": " + String.format(Locale.ROOT, "%.2f", amount);
    }
}
