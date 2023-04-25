import org.junit.Test;
import static org.junit.Assert.*;
import com.logic.feature.Customer;
import com.logic.feature.Member;
import com.logic.feature.VIP;

public class UserTest {
    @Test
    public void userTest() {
        Customer cst = new Customer(1);
        cst.makePayment();
        Member mmb = cst.upgradeToMember("abc", "123"); /* Upgrade to Member */

        VIP vip = mmb.upgradeToVIP(mmb.getName(), mmb.getPhoneNumber()); /* Upgrade to VIP */

        VIP testVIP = new VIP(1, "abc", "123");
        assertEquals(vip.getId(), testVIP.getId());
        assertEquals(vip.getName(), testVIP.getName());
        assertEquals(vip.getPhoneNumber(), testVIP.getPhoneNumber());
        assertEquals(vip.getPaymentHistory(), testVIP.getPaymentHistory());
    }
}
