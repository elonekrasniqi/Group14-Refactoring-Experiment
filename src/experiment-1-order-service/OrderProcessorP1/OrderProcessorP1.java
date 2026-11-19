package OrderProcessorP1;

import java.util.Locale;

public class OrderProcessorP1 {

    private static final double DISCOUNT_RATE = 0.10;
    private static final double TAX_RATE = 0.18;

    public String processOrder(String customer, double price,
                               int quantity, boolean discount) {

        String name = normalizeCustomerName(customer);

        double subtotal = price * quantity;

        double discountedTotal = subtotal;

        if (discount) {
            discountedTotal = discountedTotal
                    - discountedTotal * DISCOUNT_RATE;
        }

        double tax = discountedTotal * TAX_RATE;
        double finalTotal = discountedTotal + tax;

        return formatResult(name, finalTotal);
    }

    public String processRefund(String customer, double amount) {

        String name = normalizeCustomerName(customer);

        return formatResult(name, amount);
    }

    private String normalizeCustomerName(String customer) {
        return customer.trim().toUpperCase(Locale.ROOT);
    }

    private String formatResult(String name, double amount) {
        return name + ": "
                + String.format(Locale.ROOT, "%.2f", amount);
    }
}
