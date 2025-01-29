package com.jamersc.springboot.financialhub.model;


public enum SecSubType {

    SEC_REGISTRATION("SEC Registration"),
    AMENDMENT_OF_ARTICLES_OF_INCORPORATION("Amendment of Articles of Incorporation"),
    INCREASE_IN_AUTHORIZED_CAPITAL_STOCK("Increase in Authorized Capital Stock");

    private final String secSubType;

    SecSubType(String secSubType) {
        this.secSubType = secSubType;
    }

    public String displaySecSubType() {
        return secSubType;
    }
}
