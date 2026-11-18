package variants.GPT_p2;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceGPTP2 {

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
                .append(formatCustomerName(customer))
                .append("\n");

        double subtotal = 0;
        double tax = 0;

        for (Item it : items) {
            double line = calculateLineTotal(it);

            subtotal += line;
            tax += calculateTax(it, line);

            appendInvoiceItem(sb, it, line);
        }

        double discount = calculateDiscount(tier, subtotal);
        double total = subtotal - discount + tax;

        appendInvoiceSummary(sb, subtotal, discount, tax, total);

        return sb.toString();
    }

    // 1. Customer name formatting
    private String formatCustomerName(String customer) {
        return customer.trim().toUpperCase();
    }

    // 2. Calculate item price with quantity discount
    private double calculateLineTotal(Item item) {
        double line = item.price * item.qty;

        if (item.qty >= 10) {
            line = line - line * 0.05;
        }

        return line;
    }

    // 3. Calculate tax based on item category
    private double calculateTax(Item item, double line) {
        if (item.category.equals("FOOD")) {
            return line * 0.08;
        } else if (item.category.equals("BOOK")) {
            return line * 0.0;
        } else {
            return line * 0.18;
        }
    }

    // 4. Format one invoice item
    private void appendInvoiceItem(StringBuilder sb, Item item, double line) {
        sb.append(item.name).append(" x").append(item.qty).append(" = ")
                .append(String.format("%.2f", line)).append("\n");
    }

    // 5. Calculate customer discount
    private double calculateDiscount(String tier, double subtotal) {
        if (tier.equals("GOLD")) {
            return subtotal * 0.10;
        } else if (tier.equals("SILVER")) {
            return subtotal * 0.05;
        }

        return 0;
    }

    // 6. Format invoice financial summary
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

    public String buildShippingLabel(String customer, String city, String country) {
        StringBuilder sb = new StringBuilder();

        sb.append("SHIP TO: ")
                .append(formatCustomerName(customer))
                .append("\n");

        sb.append(city.trim().toUpperCase())
                .append(", ")
                .append(country.trim().toUpperCase())
                .append("\n");

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
