import org.junit.Test;
//import static org.junit.Assert.*;
//import java.util.*;
import com.logic.feature.*;

public class InventoryTest {
    @Test
    public void testInventory() {
        Product product1 = new Product(5, "Alpha", 100, 100, "desc-1");
        Product product2 = new Product(5, "Beta", 100, 100, "desc-2");
        Product product3 = new Product(5, "Gamma", 100, 100, "desc-3");
        Inventory inventory = new Inventory();
        // Test Empty Inventory
        inventory.printInventory();

        // Test add product to inventory
        inventory.addInventoryProduct(product1);
        inventory.printInventory();
        inventory.addInventoryProduct(product2);
        inventory.addInventoryProduct(product3);
        inventory.printInventory();

        // Test add products that are already in inventory
        inventory.addInventoryProduct(product3);

        // Test remove product
        inventory.removeInventoryProduct(product2);
        inventory.printInventory();

        // Test update inventory
        product1.setCount(100);
        inventory.updateInventoryProduct(product1);
        inventory.printInventory();

        // Test search product in inventory
        Product res = inventory.searchInventoryProduct(product2.getProductName());
        if (res == null)
            System.out.println("Product Not Found");
        else
            res.printProduct();

        res = inventory.searchInventoryProduct(product1.getProductName());
        if (res == null)
            System.out.println("Product Not Found");
        else
            res.printProduct();
    }
}