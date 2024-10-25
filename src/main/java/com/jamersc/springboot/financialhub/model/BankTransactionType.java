package com.jamersc.springboot.financialhub.model;

public enum BankTransactionType {
    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal");

    private final String bankTransactionType;

    BankTransactionType(String bankTransactionType) {
        this.bankTransactionType = bankTransactionType;
    }

    public String displayBankTransactionType() {
        return bankTransactionType;
    }
}
