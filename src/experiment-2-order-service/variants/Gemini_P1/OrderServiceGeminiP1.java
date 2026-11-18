package variants.Gemini_P1;

import java.util.List;

public class OrderServiceGeminiP1 {

    private static final int BULK_QTY_THRESHOLD = 10;
    private static final double BULK_DISCOUNT_RATE = 0.05;

    private static final double TAX_RATE_FOOD = 0.08;
    private static final double TAX_RATE_BOOK = 0.0;
    private static final double TAX_RATE_DEFAULT = 0.18;

    private static final double DISCOUNT_RATE_GOLD = 0.10;
    private static final double DISCOUNT_RATE_SILVER = 0.05;

    public record Item(String name, double price, int qty, String category) {}

    public String buildInvoice(String customer, String tier, List<Item> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("INVOICE for ").append(formatString(customer)).append("\n");

        double subtotal = 0;
        double tax = 0;

        for (Item it : items) {
            double lineTotal = calculateLineTotal(it);
            subtotal += lineTotal;
            tax += calculateTax(it.category(), lineTotal);

            sb.append(it.name()).append(" x").append(it.qty()).append(" = ")
                    .append(formatCurrency(lineTotal)).append("\n");
        }

        double discount = calculateDiscount(tier, subtotal);
        double total = subtotal - discount + tax;

        sb.append("Subtotal: ").append(formatCurrency(subtotal)).append("\n");
        sb.append("Discount: ").append(formatCurrency(discount)).append("\n");
        sb.append("Tax: ").append(formatCurrency(tax)).append("\n");
        sb.append("Total: ").append(formatCurrency(total)).append("\n");

        return sb.toString();
    }

    public String buildShippingLabel(String customer, String city, String country) {
        return "SHIP TO: " + formatString(customer) + "\n" +
                formatString(city) + ", " + formatString(country) + "\n";
    }

    public List<String> names(List<Item> items) {
        return items.stream()
                .map(Item::name)
                .toList();
    }

    private double calculateLineTotal(Item it) {
        double line = it.price() * it.qty();
        return it.qty() >= BULK_QTY_THRESHOLD ? line * (1.0 - BULK_DISCOUNT_RATE) : line;
    }

    private double calculateTax(String category, double lineTotal) {
        return lineTotal * switch (category) {
            case "FOOD" -> TAX_RATE_FOOD;
            case "BOOK" -> TAX_RATE_BOOK;
            default -> TAX_RATE_DEFAULT;
        };
    }

    private double calculateDiscount(String tier, double subtotal) {
        return subtotal * switch (tier) {
            case "GOLD" -> DISCOUNT_RATE_GOLD;
            case "SILVER" -> DISCOUNT_RATE_SILVER;
            default -> 0.0;
        };
    }

    private String formatString(String str) {
        return str.trim().toUpperCase();
    }

    private String formatCurrency(double amount) {
        return String.format("%.2f", amount);
    }
}