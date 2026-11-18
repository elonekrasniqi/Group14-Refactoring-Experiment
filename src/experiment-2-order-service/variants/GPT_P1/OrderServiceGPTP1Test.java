package variants.GPT_P1;

import java.util.List;

/** Minimal behaviour-preservation tests (no external dependencies). Run before AND after refactoring. */
public class OrderServiceGPTP1Test {
    static int failed = 0;
    static void check(String name, boolean ok) {
        System.out.println((ok ? "PASS " : "FAIL ") + name);
        if (!ok) failed++;
    }
    public static void main(String[] a) {
        OrderServiceGPTP1 s = new OrderServiceGPTP1();
        List<OrderServiceGPTP1.Item> items = List.of(
            new OrderServiceGPTP1.Item("Pen", 2.00, 10, "OTHER"),
            new OrderServiceGPTP1.Item("Bread", 1.50, 4, "FOOD"),
            new OrderServiceGPTP1.Item("Novel", 12.00, 1, "BOOK"));
        String gold = s.buildInvoice("  ardit ", "GOLD", items);
        check("gold total", gold.contains("Total: 37.20"));
        check("gold discount", gold.contains("Discount: 3.70"));
        check("bulk discount line", gold.contains("Pen x10 = 19.00"));
        String silver = s.buildInvoice("x", "SILVER", items);
        check("silver discount", silver.contains("Discount: 1.85"));
        String none = s.buildInvoice("x", "BASIC", items);
        check("no discount", none.contains("Discount: 0.00"));
        check("header upper-case trim", gold.startsWith("INVOICE for ARDIT\n"));
        check("shipping label", s.buildShippingLabel(" ana ", " prishtine ", "xk").equals("SHIP TO: ANA\nPRISHTINE, XK\n"));
        System.out.println(failed == 0 ? "ALL PASSED" : failed + " FAILED");
        System.exit(failed == 0 ? 0 : 1);
    }
}
