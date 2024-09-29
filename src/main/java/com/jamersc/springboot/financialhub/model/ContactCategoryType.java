package com.jamersc.springboot.financialhub.model;

public enum ContactCategoryType {
    CLIENT,
    VENDOR,
    INTERNAL;

    public String convertContactCategoryType() {
        // Capitalize the first letter and make the rest lowercase
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}
