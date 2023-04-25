package com.logic.feature;

import java.util.*;
import lombok.*;

@NoArgsConstructor
public class Inventory {
    // Attributes
    private Map<String, Product> storage = new HashMap<String, Product>();

    // Method
    public int getInventoryCount() {
        return this.storage.size();
    }

    public Product searchInventoryProduct(String productName) {
        if (!this.storage.containsKey(productName)) {
            // Product Not Found
            return null;
        } else {
            // Product Found
            return this.storage.get(productName);
        }
    }

    public void addInventoryProduct(Product newProduct) {
        Product productFound = this.searchInventoryProduct(newProduct.getProductName());
        if (productFound != null && productFound.isEquals(newProduct)) {
            // The product already exists in storage
            // Add count of product
            productFound.updateCount(newProduct.getCount());
        } else {
            // productFound == null || productFound.isEquals(newProduct)
            // Product not in storage, add a new product
            this.storage.put(newProduct.getProductName(), newProduct);
        }
    }

    public void removeInventoryProduct(Product product) {
        if (this.storage.containsKey(product.getProductName())) {
            // Product Found, remove product
            this.storage.remove(product.getProductName());
        } // Product not found, do nothing
    }

    public void updateInventoryProduct(Product product) {
        if (this.storage.containsKey(product.getProductName())) {
            // Product Found, update product
            this.storage.put(product.getProductName(), product);
        } // Product Not Found, do nothing
    }

    public void printInventory() {
        if (getInventoryCount() == 0) {
            System.out.println("Empty Inventory");
        } else {
            System.out.println("Product in inventory:");
            int i = 1;
            for (Map.Entry<String, Product> entry : this.storage.entrySet()) {
                System.out.println(i + ". " + entry.getKey() + " count: " + entry.getValue().getCount());
                i++;
            }
        }
    }
}
