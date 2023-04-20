import java.util.ArrayList;
import java.util.List;

public class Product {
    private int count;
    private String productName;
    private float basePrice;
    private float boughtPrice;
    private String category;

    // Constructor
    public Product(int count, String productName, float basePrice, float boughtPrice, String category) {
        this.count = count;
        this.productName = productName;
        this.basePrice = basePrice;
        this.boughtPrice = boughtPrice;
        this.category = category;
    }

    // Getters and setters
    public int getCount() {
        return count;
    }

     public String getProductName() {
        return productName;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public float getBoughtPrice() {
        return boughtPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public void setBoughtPrice(float boughtPrice) {
        this.boughtPrice = boughtPrice;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Method to update count
    public void updateCount(int amount) {
        count += amount;
    }
}
