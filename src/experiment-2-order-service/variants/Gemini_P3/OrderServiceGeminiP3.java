package variants.Gemini_P3;

import java.util.ArrayList;
import java.util.List;

/** Refactored code applying Extract Method to buildInvoice. */
public class OrderServiceGeminiP3 {

    public static class Item {
        final String name; final double price; final int qty; final String category;
        public Item(String name, double price, int qty, String category) {
            this.name = name; this.price = price; this.qty = qty; this.category = category;
        }
    }

    public String buildInvoice(String customer, String tier, List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("INVOICE for ").append(customer.trim().toUpperCase()).append("\n");

        double subtotal = 0;
        double tax = 0;

        for (Item it : items) {
            double line = calculateLineAmount(it);
            subtotal += line;
            tax += calculateTax(it.category, line);

            sb.append(it.name).append(" x").append(it.qty).append(" = ")
                    .append(formatCurrency(line)).append("\n");
        }

        double discount = calculateDiscount(tier, subtotal);
        double total = subtotal - discount + tax;

        sb.append("Subtotal: ").append(formatCurrency(subtotal)).append("\n");
        sb.append("Discount: ").append(formatCurrency(discount)).append("\n");
        sb.append("Tax: ").append(formatCurrency(tax)).append("\n");
        sb.append("Total: ").append(formatCurrency(total)).append("\n");

        return sb.toString();
    }

    private double calculateLineAmount(Item it) {
        double line = it.price * it.qty;
        if (it.qty >= 10) {
            line = line - line * 0.05;
        }
        return line;
    }

    private double calculateTax(String category, double lineAmount) {
        if (category.equals("FOOD")) {
            return lineAmount * 0.08;
        } else if (category.equals("BOOK")) {
            return 0.0;
        } else {
            return lineAmount * 0.18;
        }
    }

    private double calculateDiscount(String tier, double subtotal) {
        if (tier.equals("GOLD")) {
            return subtotal * 0.10;
        } else if (tier.equals("SILVER")) {
            return subtotal * 0.05;
        }
        return 0.0;
    }

    private String formatCurrency(double amount) {
        return String.format("%.2f", amount);
    }

    // Unmodified methods below
    public String buildShippingLabel(String customer, String city, String country) {
        StringBuilder sb = new StringBuilder();
        sb.append("SHIP TO: ").append(customer.trim().toUpperCase()).append("\n");
        sb.append(city.trim().toUpperCase()).append(", ").append(country.trim().toUpperCase()).append("\n");
        return sb.toString();
    }

    public List<String> names(List<Item> items) {
        List<String> r = new ArrayList<>();
        for (Item it : items) r.add(it.name);
        return r;
    }
}