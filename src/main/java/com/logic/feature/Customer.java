/* Customer Class */
package com.logic.feature;
import com.logic.feature.interfaces.Payable;

import lombok.*;

@Getter

public class Customer implements Payable {
    /* Attributes */
    protected int id;
    protected boolean madeFirstPurchase;

    /* Constructor */
    public Customer(int id) {
        this.id = id;
        this.madeFirstPurchase = false;
    }

    /* Setter */
    public void setMadeFirstPurchase(boolean value) {
        this.madeFirstPurchase = value;
    }

    /* Other Methods */
    public void makePayment() {
        this.setMadeFirstPurchase(true);
    }

    /* Upgrade to Member*/
    /* Can only be used if madeFirstPurchase = true */
    public Member upgradeToMember(String name, String phoneNumber) {
        return new Member(this.getId(), name, phoneNumber);
    }
}


