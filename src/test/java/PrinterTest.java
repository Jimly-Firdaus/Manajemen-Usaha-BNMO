import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.logic.feature.*;
import com.logic.output.Printer;

public class PrinterTest {
    @Test
    public void testPrintBills() {
        // Create some test data
        List<Bill> bills = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product1 = new Product(i, "product-" + i, 100, 100, "desc-" + i);
            Product product2 = new Product(i, "product-" + (i + 1), 100, 100, "desc-" + i);
            ListOfProduct lsProduct = new ListOfProduct();
            lsProduct.addProduct(product1);
            lsProduct.addProduct(product2);
            bills.add(new Bill(lsProduct, i + 100, i % 2 == 0 ? false : true, false));
        };

        // Call the method to run on other thread
        Thread printer = new Printer<>(bills, "Bills-Test.pdf");
        printer.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Check that the output file was created
        File file = new File("Bills-Test.pdf");
        assertTrue(file.exists());
    }
}