package com.logic.feature;

import java.io.Serializable;
import java.util.*;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class Inventory implements Serializable {
    // Attributes   
    private List<Product> storage = new ArrayList<Product>();

    // Method
    public int getInventoryCount() {
        return this.storage.size();
    }

    public Product searchInventoryProduct(String productName) {
        for (Product product : storage) {
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }
        return null;
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
            this.storage.add(newProduct);
        }
    }

    public void removeInventoryProduct(String productName) {
        Product productFound = this.searchInventoryProduct(productName);
        if (productFound != null) {
            // Product Found, remove product
            this.storage.remove(productFound);
        } // Product not found, do nothing
    }

    public void updateInventoryProduct(String oldProductName, Product newProduct) {
        Product oldProduct = this.searchInventoryProduct(oldProductName);
        if (oldProduct != null) {
            // Product Found, update product
            int index = this.storage.indexOf(oldProduct);
            this.storage.set(index, newProduct);
        } // Product Not Found, do nothing
    }

    public void printInventory() {
        if (this.getInventoryCount() == 0) {
            System.out.println("Empty Inventory");
        } else {
            System.out.println("Product in inventory:");
            int i = 1;
            for (Product product : this.storage) {
                System.out.print(i + ". ");
                product.printProduct();
                i++;
            }
        }
    }
}
