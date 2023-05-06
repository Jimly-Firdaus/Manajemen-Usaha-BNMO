package com.logic.feature;
import java.io.Serializable;
import java.util.*;
import lombok.*;

@NoArgsConstructor
@Getter
public class ListOfProduct implements Serializable{
    /*Attribute */
    private Map<String, Product> productList = new HashMap<String, Product>();

    /*Method */
    public int getProductCount(){
        return this.productList.size();
    }

    public Product searchProduct(String productName){
        if(this.productList.get(productName) != null){
            return this.productList.get(productName);
        }
        else{
            return null;
        }
    }

    public void addProduct(Product product){
        this.productList.put(product.getProductName(), product);
    }

    public void removeProduct(String productName){
        this.productList.remove(productName);
    }

    public void printAllProduct(){
        if(getProductCount() == 0){
            System.out.println("No product");
        }else{
            for (String key : this.productList.keySet()) {
                System.out.println(key);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Product product : productList.values()) {
            sb.append(i).append(". ").append(product.toString()).append(",\n");
            i++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Product product1 = new Product(2, "product-1", 100, 100, "desc-1");
        Product product2 = new Product(3, "product-2", 100, 100, "desc-2");
        Product product4 = new Product(4, "product-3", 100, 100, "desc-3");
        ListOfProduct productList = new ListOfProduct();
        productList.printAllProduct();
        productList.addProduct(product1);
        productList.printAllProduct();

    }
}
