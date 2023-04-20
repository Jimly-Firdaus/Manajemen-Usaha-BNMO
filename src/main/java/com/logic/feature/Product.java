package com.logic.feature;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int count;
    private String productName;
    private float basePrice;
    private float boughtPrice;
    private String category;

    public void updateCount(int amount) {
        count += amount;
    }
}
