package com.jamersc.springboot.financialhub.model.bank;

public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL;

    public String convertTransactionType() {
        // Capitalize the first letter and make the rest lowercase
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}
