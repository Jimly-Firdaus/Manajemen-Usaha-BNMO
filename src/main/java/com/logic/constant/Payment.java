package com.logic.constant;

import com.logic.feature.ListOfProduct;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment {
    /*Attribute */
    private int userID;
    private ListOfProduct boughtItems;
    private float totalPrice;
}
