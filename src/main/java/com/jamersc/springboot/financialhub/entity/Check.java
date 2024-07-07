package com.jamersc.springboot.financialhub.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Check {
    private int id;
    private String checkVoucherNo;
    private double checkAmount;
    private String checkAmountInWords;

/*    public Check(String checkVoucherNo, double checkAmount, String checkAmountInWords) {
        this.checkVoucherNo = checkVoucherNo;
        this.checkAmount = checkAmount;
        this.checkAmountInWords = checkAmountInWords;
    }*/
}
