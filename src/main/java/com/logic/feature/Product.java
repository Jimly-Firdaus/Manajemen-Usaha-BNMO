package com.logic.feature;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Product {
    /*Attribute */
    private int count;
    @NonNull private String productName;
    private float basePrice;
    private float boughtPrice;
    @NonNull private String category;
}
