package com.logic.feature;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    private int count;
    @NonNull private String productName;
    private float basePrice;
    private float boughtPrice;
    @NonNull private String category;

    public void updateCount(int amount) {
        count += amount;
    }
}
