/* Customer Class */
package com.logic.feature;
import com.logic.feature.interfaces.Payable;

import lombok.*;

@NoArgsConstructor
public class Customer implements Payable {
    /* Attributes */
    protected int id;
    protected boolean madeFirstPurchase;

    /* Constructor */
    public Customer(int id) {
        this.id = id;
        this.madeFirstPurchase = false;
    }

    public int getId() {
        return this.id;
    }

    public boolean getFirstPurchaseStatus() {
        return this.madeFirstPurchase;
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


