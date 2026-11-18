package variants.GPT_P1;

import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceGPTP1 {

    // Constants
    private static final int BULK_DISCOUNT_THRESHOLD = 10;

    private static final double BULK_DISCOUNT_RATE = 0.05;

    private static final double FOOD_TAX_RATE = 0.08;
    private static final double BOOK_TAX_RATE = 0.0;
    private static final double STANDARD_TAX_RATE = 0.18;

    private static final double GOLD_DISCOUNT_RATE = 0.10;
    private static final double SILVER_DISCOUNT_RATE = 0.05;

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

    // Main invoice method
    public String buildInvoice(String customer, String tier, List<Item> items) {

        StringBuilder sb = new StringBuilder();

        appendInvoiceHeader(sb, customer);

        double subtotal = 0;
        double tax = 0;

        for (Item item : items) {

            double line = calculateLinePrice(item);

            subtotal += line;
            tax += calculateTax(line, item.category);

            appendInvoiceItem(sb, item, line);
        }

        double discount = calculateDiscount(subtotal, tier);

        double total = subtotal - discount + tax;

        appendInvoiceSummary(sb, subtotal, discount, tax, total);

        return sb.toString();
    }

    // Extracted: Invoice header
    private void appendInvoiceHeader(StringBuilder sb, String customer) {
        sb.append("INVOICE for ")
                .append(normalizeName(customer))
                .append("\n");
    }

    // Extracted: Item price calculation
    private double calculateLinePrice(Item item) {

        double line = item.price * item.qty;

        if (item.qty >= BULK_DISCOUNT_THRESHOLD) {
            line -= line * BULK_DISCOUNT_RATE;
        }

        return line;
    }

    // Extracted: Tax calculation
    private double calculateTax(double line, String category) {

        double taxRate;

        if (category.equals("FOOD")) {
            taxRate = FOOD_TAX_RATE;
        } else if (category.equals("BOOK")) {
            taxRate = BOOK_TAX_RATE;
        } else {
            taxRate = STANDARD_TAX_RATE;
        }

        return line * taxRate;
    }

    // Extracted: Customer discount calculation
    private double calculateDiscount(double subtotal, String tier) {

        double discountRate = 0;

        if (tier.equals("GOLD")) {
            discountRate = GOLD_DISCOUNT_RATE;
        } else if (tier.equals("SILVER")) {
            discountRate = SILVER_DISCOUNT_RATE;
        }

        return subtotal * discountRate;
    }

    // Extracted: Invoice item formatting
    private void appendInvoiceItem(StringBuilder sb, Item item, double line) {

        sb.append(item.name)
                .append(" x")
                .append(item.qty)
                .append(" = ")
                .append(String.format("%.2f", line))
                .append("\n");
    }

    // Extracted: Invoice summary formatting
    private void appendInvoiceSummary(
            StringBuilder sb,
            double subtotal,
            double discount,
            double tax,
            double total) {

        sb.append("Subtotal: ").append(String.format("%.2f", subtotal)).append("\n");
        sb.append("Discount: ").append(String.format("%.2f", discount)).append("\n");
        sb.append("Tax: ").append(String.format("%.2f", tax)).append("\n");
        sb.append("Total: ").append(String.format("%.2f", total)).append("\n");
    }

    // Shared customer name formatting
    private String normalizeName(String name) {
        return name.trim().toUpperCase();
    }

    // Shipping label
    public String buildShippingLabel(String customer, String city, String country) {

        StringBuilder sb = new StringBuilder();

        sb.append("SHIP TO: ")
                .append(normalizeName(customer))
                .append("\n");

        sb.append(normalizeName(city))
                .append(", ")
                .append(normalizeName(country))
                .append("\n");

        return sb.toString();
    }

    // Extract names using Streams
    public List<String> names(List<Item> items) {

        return items.stream()
                .map(item -> item.name)
                .collect(Collectors.toList());
    }
}
