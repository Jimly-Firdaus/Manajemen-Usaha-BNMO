package com.logic.feature;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
class Product {
    /*Attribute */
    private int count;
    private String productName;
    private float basePrice;
    private float boughtPrice;
    private String category;
}
