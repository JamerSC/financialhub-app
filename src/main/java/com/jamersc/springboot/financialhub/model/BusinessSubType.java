package com.jamersc.springboot.financialhub.model;

public enum BusinessSubType {

    BUSINESS_REGISTRATION("Business Registration"),
    BUSINESS_RENEWAL("Business Renewal"),
    BUSINESS_CLOSURE("Business Closure"),
    OTHERS("Others");

    private final String businessSubType;

    BusinessSubType(String businessSubType) {
        this.businessSubType = businessSubType;
    }

    public String displayBusinessSubType() {
        return businessSubType;
    }
}
