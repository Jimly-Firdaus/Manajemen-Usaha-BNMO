package com.logic.constant;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.logic.constant.interfaces.IPayment;
import com.logic.feature.Product;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Payment implements IPayment, Serializable {
    /*Attribute */
    private int userID;
    private List<Product> boughtItems = new ArrayList<>();
    private float totalPrice;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User ID : ").append(userID).append("\n");
        sb.append("=> boughtItems = \n");
        int i = 1;
        for (Product product : this.boughtItems) {
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
