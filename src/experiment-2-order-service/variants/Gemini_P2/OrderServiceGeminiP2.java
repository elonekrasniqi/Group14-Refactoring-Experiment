package variants.Gemini_P2;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceGeminiP2 {

    public static class Item {
        final String name; final double price; final int qty; final String category;
        public Item(String name, double price, int qty, String category) {
            this.name = name; this.price = price; this.qty = qty; this.category = category;
        }
    }

    public String buildInvoice(String customer, String tier, List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("INVOICE for ").append(formatText(customer)).append("\n");

        double subtotal = 0;
        double tax = 0;

        for (Item it : items) {
            double line = calculateLineTotal(it);
            subtotal += line;
            tax += calculateTax(line, it.category);

            sb.append(it.name).append(" x").append(it.qty).append(" = ")
                    .append(formatCurrency(line)).append("\n");
        }

        double discount = calculateDiscount(subtotal, tier);
        double total = subtotal - discount + tax;

        sb.append("Subtotal: ").append(formatCurrency(subtotal)).append("\n");
        sb.append("Discount: ").append(formatCurrency(discount)).append("\n");
        sb.append("Tax: ").append(formatCurrency(tax)).append("\n");
        sb.append("Total: ").append(formatCurrency(total)).append("\n");

        return sb.toString();
    }

    public String buildShippingLabel(String customer, String city, String country) {
        StringBuilder sb = new StringBuilder();
        sb.append("SHIP TO: ").append(formatText(customer)).append("\n");
        sb.append(formatText(city)).append(", ").append(formatText(country)).append("\n");
        return sb.toString();
    }

    public List<String> names(List<Item> items) {
        List<String> r = new ArrayList<>();
        for (Item it : items) {
            r.add(it.name);
        }
        return r;
    }

    // --- Extracted Helper Methods ---

    private String formatText(String text) {
        return text.trim().toUpperCase();
    }

    private double calculateLineTotal(Item it) {
        double line = it.price * it.qty;
        if (it.qty >= 10) {
            line -= line * 0.05;
        }
        return line;
    }

    private double calculateTax(double lineAmount, String category) {
        if (category.equals("FOOD")) {
            return lineAmount * 0.08;
        } else if (category.equals("BOOK")) {
            return 0.0;
        } else {
            return lineAmount * 0.18;
        }
    }

    private double calculateDiscount(double subtotal, String tier) {
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
}