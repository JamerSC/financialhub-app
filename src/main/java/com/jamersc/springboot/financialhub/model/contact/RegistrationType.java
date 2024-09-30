package com.jamersc.springboot.financialhub.model.contact;

public enum RegistrationType {
    CORPORATION,
    PARTNERSHIP,
    SINGLE_PROPRIETORSHIP,
    FOUNDATION,
    ASSOCIATION,
    OTHERS;

    public String convertRegistrationType() {
        // Capitalize the first letter and make the rest lowercase
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}
