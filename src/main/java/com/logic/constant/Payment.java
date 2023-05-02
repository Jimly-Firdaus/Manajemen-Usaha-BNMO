package com.logic.constant;

import lombok.*;

import com.logic.constant.interfaces.IPayment;
import com.logic.feature.ListOfProduct;
import com.logic.feature.Product;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment implements IPayment {
    /*Attribute */
    private int userID;
    private ListOfProduct boughtItems;
    private float totalPrice;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User ID : ").append(userID).append("\n");
        sb.append("=> boughtItems = \n");
        int i = 1;
        for (Product product : boughtItems.getProductList().values()) {
            sb.append("    ").append(i).append(". ").append(product.getProductName())
                    .append(" (count : ").append(product.getCount())
                    .append(", basePrice : ").append(product.getBasePrice())
                    .append(", boughtPrice : ").append(product.getBoughtPrice())
                    .append(", category : ").append(product.getCategory()).append("),\n");
            i++;
        }
        sb.append("=> totalPrice = ").append(totalPrice).append("\n");
        return sb.toString();
    }
}
