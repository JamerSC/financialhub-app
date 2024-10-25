package com.jamersc.springboot.financialhub.model;

public enum ContactCategoryType {
    INTERNAL("Internal"),
    CLIENT("Client"),
    VENDOR("Vendor");

    private final String contactCategoryType;

    ContactCategoryType(String contactCategoryType) {
        this.contactCategoryType = contactCategoryType;
    }

    public String displayContactCategoryType() {
        return contactCategoryType;
    }
    /*public String convertContactCategoryType() {
        // Capitalize the first letter and make the rest lowercase
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }*/
}
