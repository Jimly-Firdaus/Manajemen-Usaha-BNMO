import org.junit.Test;
import static org.junit.Assert.*;
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
        System.out.println("\nAdding test");
        inventory.addInventoryProduct(product1);
        inventory.printInventory();
        inventory.addInventoryProduct(product2);
        inventory.addInventoryProduct(product3);
        inventory.printInventory();

        // Test add products that are already in inventory
        inventory.addInventoryProduct(product3);

        // Test remove product
        System.out.println("\nRemove Test");
        inventory.removeInventoryProduct(product2.getProductName());
        inventory.printInventory();

        // Test update inventory
        System.out.println("\nUpdate Test");
        String oldProductName = product1.getProductName();
        product1.setCount(50);
        product1.setProductName("Tetha");
        Product newProduct = product1;
        inventory.updateInventoryProduct(oldProductName, product1);
        Product res = inventory.searchInventoryProduct(product1.getProductName());
        assertEquals(res.getProductName(), newProduct.getProductName());
        assertEquals(res.getCount(), newProduct.getCount());
        assertEquals(res.getCategory(), newProduct.getCategory());
        inventory.printInventory();

        // Test search product in inventory
        System.out.println("\nSearching test");
        res = inventory.searchInventoryProduct(product2.getProductName());
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