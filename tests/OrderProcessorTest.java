public class OrderProcessorTest {
    private static int passed = 0;

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + ", got: " + actual);
        }
        passed++;
    }

    public static void main(String[] args) {
        OrderProcessor p = new OrderProcessor();
        check("ELONA: 106.20", p.processOrder(" elona ", 100, 1, true));
        check("ARDIT: 118.00", p.processOrder("ardit", 100, 1, false));
        check("TEST: 212.40", p.processOrder("test", 100, 2, true));
        check("USER: 0.00", p.processOrder("user", 0, 1, false));
        check("ELONA: 50.00", p.processRefund(" elona ", 50));
        check("ARDIT: 0.00", p.processRefund("ardit", 0));
        check("TEST: 10.50", p.processRefund(" test ", 10.5));
        System.out.println("ALL PASSED: " + passed + "/7");
    }
}
