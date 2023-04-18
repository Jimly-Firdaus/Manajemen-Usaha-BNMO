public class TestUser {
    /* Tester */
    public static void main(String[] args) {
        /* Test Customer */
        Customer testCst = new Customer(10);
        System.out.println(testCst.getId());
        System.out.println(testCst.getMadeFirstPurchase());

        /* Test Member  */
        Member testMmb = new Member(100, "xyz", "67890");
        System.out.println(testMmb.getId());
        System.out.println(testMmb.getName());
        System.out.println(testMmb.getPhoneNumber());
        System.out.println(testMmb.getPoint());

        /* Test VIP */
        VIP testVIP = new VIP(1, "abc", "12345");
        System.out.println(testVIP.getId());
        System.out.println(testVIP.getName());
        System.out.println(testVIP.getPhoneNumber());
        System.out.println(testVIP.getPoint());
        System.out.println(testVIP.getDiscountRate());
    }
}
