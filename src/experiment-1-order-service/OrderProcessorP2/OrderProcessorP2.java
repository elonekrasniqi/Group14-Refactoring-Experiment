package OrderProcessorP2;

import java.util.Locale;

public class OrderProcessorP2 {

    public String processOrder(String customer, double price,
                               int quantity, boolean discount) {
        String name = normalizeCustomerName(customer);
        double total = calculateSubtotal(price, quantity, discount);
        total = applyTax(total);
        return formatResult(name, total);
    }

    public String processRefund(String customer, double amount) {
        String name = normalizeCustomerName(customer);
        return formatResult(name, amount);
    }

    private String normalizeCustomerName(String customer) {
        return customer.trim().toUpperCase(Locale.ROOT);
    }

    private double calculateSubtotal(double price, int quantity,
                                     boolean discount) {
        double total = price * quantity;

        if (discount) {
            total = total - total * 0.10;
        }

        return total;
    }

    private double applyTax(double total) {
        double tax = total * 0.18;
        return total + tax;
    }

    private String formatResult(String name, double amount) {
        return name + ": " +
               String.format(Locale.ROOT, "%.2f", amount);
    }
}
