import org.junit.Test;
import static org.junit.Assert.*;
import com.logic.feature.Customer;
import com.logic.feature.Member;
import com.logic.feature.VIP;

public class UserTest {
    @Test
    /* Tester */
    public static void main(String[] args) {
        /* Test Customer */
        Customer testCst = new Customer(10);
        System.out.println(testCst.getId());
        System.out.println(testCst.isMadeFirstPurchase());

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

        /* Test Upgrade */
        Customer cst = new Customer(1);
        cst.makePayment();
        Member mmb = cst.upgradeToMember("abc", "123"); /* Upgrade to Member */

        System.out.println(mmb.getId());
        System.out.println(mmb.getName());
        System.out.println(mmb.getPhoneNumber());
        System.out.println(mmb.getPaymentHistory());
        VIP vip = mmb.upgradeToVIP(mmb.getName(), mmb.getPhoneNumber()); /* Upgrade to VIP */

        testVIP = new VIP(1, "abc", "123");
        assertEquals(vip.getId(), testVIP.getId());
        assertEquals(vip.getName(), testVIP.getName());
        assertEquals(vip.getPhoneNumber(), testVIP.getPhoneNumber());
        assertEquals(vip.getPaymentHistory(), testVIP.getPaymentHistory());
    }
}
