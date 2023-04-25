package com.logic.feature;
import lombok.*;

@NoArgsConstructor
@Setter
@Getter
public class Bill {
    /*Attribute */
    private ListOfProduct basket;
    private int idCustomer;
    private boolean isFixed;

    /*Method */
    public Product searchProduct(String productName){
        return this.basket.searchProduct(productName);
    }

    public void addProduct(Product product){
        this.basket.addProduct(product);
    }

    public void removeProduct(String productName){
        this.basket.removeProduct(productName);
    }

    public void printAllBillProduct(){
        this.basket.printAllProduct();
    }

    public void BillProductCount(){
        this.basket.getProductCount();
    }
}
