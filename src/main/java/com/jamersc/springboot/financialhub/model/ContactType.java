package com.jamersc.springboot.financialhub.model;

public enum ContactType {
    INDIVIDUAL("Individual"),
    COMPANY("Company");

    private final String contactType;

    ContactType(String contactType) {
        this.contactType = contactType;
    }

    public String displayContactType() {
        return contactType;
    }
}
