package com.logic.feature;

import java.io.Serializable;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product implements Serializable {
    private int count;
    @NonNull
    private String productName;
    private float basePrice;
    private float boughtPrice;
    @NonNull
    private String category;

    public void updateCount(int amount) {
        count += amount;
    }

    public boolean isEquals(Product productCompare) {
        return this.productName.equals(productCompare.productName) && this.basePrice == productCompare.basePrice
                && this.boughtPrice == productCompare.boughtPrice && this.category.equals(productCompare.category);
    }

    public void printProduct() {
        System.out.println("Name : " + this.productName + "\tCount : " + this.count + "\tCategory : " + this.category
                + "\tBasePrice : " + this.basePrice + "\tBoughtPrice : " + this.boughtPrice);
    }

    @Override
    public String toString() {
        return productName + " (count = " + count + ", basePrice = " + basePrice + ", boughtPrice = " + boughtPrice + ", category = " + category + ")";
    }
}
