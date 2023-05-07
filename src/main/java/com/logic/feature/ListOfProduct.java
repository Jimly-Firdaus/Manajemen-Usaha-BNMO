package com.logic.feature;
import java.io.Serializable;
import java.util.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListOfProduct implements Serializable{
    /*Attribute */
    private List<Product> productList = new ArrayList<Product>(); 

    /*Method */
    public int getTotalProduct(){
        return this.productList.size();
    }

    public Product searchProduct(String productName){
        for (Product product : this.productList){
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public void addProduct(Product product){
        this.productList.add(product);
    }

    public void removeProduct(String productName){
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductName().equals(productName)) {
                productList.remove(i);
                break;
            }
        }
    }

    public void printAllProduct(){
        if(this.getTotalProduct() == 0){
            System.out.println("No product");
        }else{
            for (Product product : productList) {
                System.out.println(product.getProductName());
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Product product : productList) {
            sb.append(i).append(". ").append(product.toString()).append(",\n");
            i++;
        }
        return sb.toString();
    }
}
