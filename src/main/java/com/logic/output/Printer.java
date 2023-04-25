package com.logic.output;
import lombok.*;
import java.util.*;

import com.logic.feature.Bill;
import com.logic.feature.Payment;

@Setter
@Getter
class Printer{
    /*Attribute */
    private ArrayList<Payment> paymentList;
    private ArrayList<Bill> billList;

    public Printer(){
        this.paymentList = new ArrayList<Payment>();
        this.billList = new ArrayList<Bill>();
    }

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