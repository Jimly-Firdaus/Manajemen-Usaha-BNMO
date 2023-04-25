package com.logic.output;
import lombok.*;
import java.util.*;

import com.logic.feature.Bill;
import com.logic.constant.Payment;

@NoArgsConstructor
@Setter
@Getter
class Printer{
    /*Attribute */
    private ArrayList<Payment> paymentList = new ArrayList<Payment>();
    private ArrayList<Bill> billList = new ArrayList<Bill>();

    public Payment getCertainPayment(int idx){
        if(idx < this.paymentList.size()){
            return this.paymentList.get(idx);
        }else{
            return null;
        }
    }

    public Bill getCertainBill(int idx){
        if(idx < this.billList.size()){
            return this.billList.get(idx);
        }else{
            return null;
        }
    }
}