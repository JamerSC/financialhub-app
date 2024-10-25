package com.jamersc.springboot.financialhub.model;


public enum PropertySubType {

    TRANSFER_OF_TITLE("Transfer of Title"),
    SETTLEMENT_OF_ESTATE("Settlement of Estate"),
    ANNOTATION("Annotation"),
    OTHERS("Others");

    private final String propertySubType;

    PropertySubType(String propertySubType) {
        this.propertySubType = propertySubType;
    }

    public String displayPropertySubType() {
        return propertySubType;
    }
}
