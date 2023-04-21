package com.logic.feature;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
class Payment {
    /*Attribute */
    private int userID;
    private ListOfProduct boughtItems;
    private float totalPrice;
}
