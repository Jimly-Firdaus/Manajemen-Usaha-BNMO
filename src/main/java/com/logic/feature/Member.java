/* Member Class */
package com.logic.feature;
import com.logic.constant.Payment;

import java.io.Serializable;
import java.util.ArrayList;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Member extends Customer implements Serializable {
    /* Attributes */
    protected String name;
    protected String phoneNumber;
    protected float point;
    protected ArrayList<Payment> paymentHistory;

    /* Constructor */
    public Member(int id, String name, String phoneNumber) {
        super(id);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = 0;
        this.paymentHistory = new ArrayList<Payment>();
    }

    /* Setter */
    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /* Other Methods */
    public void atrievePoint(int purchaseAmount) {
        this.point += purchaseAmount*(0.01); /* 1% of total purchase */
    }

    public float convertPointToDiscount() {
        return this.point; /* 1 point = 1 Rupiah */
    }

    /* Upgrade to VIP */
    public VIP upgradeToVIP(String name, String phoneNumber) {
        return new VIP(this.getId(), name, phoneNumber);
    }

    /* Add Payment to Payment History */
    public void addPayment(@NonNull Payment payment) {
        this.paymentHistory.add(payment);
    }

    /* Remove Payment from Payment History */
    public void removePayment(@NonNull Payment payment) {
        this.paymentHistory.remove(payment);
    }
}