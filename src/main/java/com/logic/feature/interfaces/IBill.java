package com.logic.feature.interfaces;

import com.logic.feature.Product;

public interface IBill {
    public Product searchProduct(String productName);
    public void addProduct(Product product);
    public void removeProduct(String productName);
    public void printAllBillProduct();
    public void BillProductCount();
    public String toString();
}
