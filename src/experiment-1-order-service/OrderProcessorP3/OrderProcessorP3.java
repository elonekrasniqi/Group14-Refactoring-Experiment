
import java.util.Locale;

public class OrderProcessorP3 {

    public String processOrder(String customer, double price,
                               int quantity, boolean discount) {
        String name = customer.trim().toUpperCase(Locale.ROOT);

        double total = price * quantity;
        total = applyDiscount(total, discount);

        double tax = calculateTax(total);
        total = total + tax;

        return formatOutput(name, total);
    }

    private double applyDiscount(double total, boolean discount) {
        if (discount) {
            total = total - total * 0.10;
        }
        return total;
    }

    private double calculateTax(double total) {
        return total * 0.18;
    }

    private String formatOutput(String name, double total) {
        return name + ": " + String.format(Locale.ROOT, "%.2f", total);
    }

    public String processRefund(String customer, double amount) {
        String name = customer.trim().toUpperCase(Locale.ROOT);
        return name + ": " + String.format(Locale.ROOT, "%.2f", amount);
    }
}
