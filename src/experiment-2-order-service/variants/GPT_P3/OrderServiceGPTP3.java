package variants.GPT_P3;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceGPTP3 {

    public static class Item {
        final String name;
        final double price;
        final int qty;
        final String category;

        public Item(String name, double price, int qty, String category) {
            this.name = name;
            this.price = price;
            this.qty = qty;
            this.category = category;
        }
    }

    public String buildInvoice(String customer, String tier, List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("INVOICE for ")
                .append(customer.trim().toUpperCase())
                .append("\n");

        double subtotal = 0;
        double tax = 0;

        for (Item it : items) {
            double line = it.price * it.qty;

            if (it.qty >= 10) {
                line = line - line * 0.05;
            }

            subtotal += line;

            tax += calculateTax(line, it.category);

            sb.append(it.name)
                    .append(" x")
                    .append(it.qty)
                    .append(" = ")
                    .append(formatAmount(line))
                    .append("\n");
        }

        double discount = calculateDiscount(subtotal, tier);
        double total = subtotal - discount + tax;

        sb.append("Subtotal: ")
                .append(formatAmount(subtotal)).append("\n");

        sb.append("Discount: ")
                .append(formatAmount(discount)).append("\n");

        sb.append("Tax: ")
                .append(formatAmount(tax)).append("\n");

        sb.append("Total: ")
                .append(formatAmount(total)).append("\n");

        return sb.toString();
    }

    // Extract Method 1: Duplicated formatting code
    private String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }

    // Extract Method 2: Tax calculation
    private double calculateTax(double line, String category) {
        if (category.equals("FOOD")) {
            return line * 0.08;
        } else if (category.equals("BOOK")) {
            return line * 0.0;
        } else {
            return line * 0.18;
        }
    }

    // Extract Method 3: Discount calculation
    private double calculateDiscount(double subtotal, String tier) {
        if (tier.equals("GOLD")) {
            return subtotal * 0.10;
        } else if (tier.equals("SILVER")) {
            return subtotal * 0.05;
        }
        return 0;
    }

    public String buildShippingLabel(String customer, String city, String country) {
        StringBuilder sb = new StringBuilder();
        sb.append("SHIP TO: ").append(customer.trim().toUpperCase()).append("\n");
        sb.append(city.trim().toUpperCase()).append(", ")
                .append(country.trim().toUpperCase()).append("\n");
        return sb.toString();
    }

    public List<String> names(List<Item> items) {
        List<String> r = new ArrayList<>();
        for (Item it : items) {
            r.add(it.name);
        }
        return r;
    }
}
