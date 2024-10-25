package com.jamersc.springboot.financialhub.model;


public enum SecSubType {

    AMENDMENT("Amendment of Articles of Incorporation"),
    INCREASE("Increase in Authorized Capital Stock");

    private final String secSubType;

    SecSubType(String secSubType) {
        this.secSubType = secSubType;
    }

    public String getSecSubType() {
        return secSubType;
    }
}
