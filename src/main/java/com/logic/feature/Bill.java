package com.logic.feature;

import lombok.*;

import java.io.Serializable;

import com.logic.feature.interfaces.IBill;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Bill implements IBill, Serializable {
    /*Attribute */
    private ListOfProduct basket;
    private int idCustomer;
    private boolean billFixed;
    private boolean billDone;

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
        this.basket.getTotalProduct();
    }

    public boolean isFixedBill(){
        return this.billFixed;
    }

    public boolean isDone(){
        return this.billDone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer ID : ").append(idCustomer).append("\n");
        sb.append("=> Basket = \n");
        int i = 1;
        for (Product product : basket.getProductList()) {
            sb.append("    ").append(i).append(". ").append(product.getProductName())
                    .append(" (count : ").append(product.getCount())
                    .append(", basePrice : ").append(product.getBasePrice())
                    .append(", boughtPrice : ").append(product.getBoughtPrice())
                    .append(", category : ").append(product.getCategory()).append("),\n");
            i++;
        }
        if (this.billFixed) {
            sb.append("=> Bill type : Fixed").append("\n");
        } else {
            sb.append("=> Bill type : Not Fixed").append("\n");
        }
        if (this.billDone) {
            sb.append("=> Bill status : Paid").append("\n");
        } else {
            sb.append("=> Bill status : Unpaid").append("\n");
        }
        return sb.toString();
    }
}
