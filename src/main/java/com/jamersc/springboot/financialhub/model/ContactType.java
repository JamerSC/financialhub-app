package com.jamersc.springboot.financialhub.model;

public enum ContactType {
    INDIVIDUAL,
    COMPANY;

    public String convertContactType() {
        // Capitalize the first letter and make the rest lowercase
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}
