package com.jamersc.springboot.financialhub.model;

public enum ClientAccountType {
    PROJECT, RETAINER, CASE;

    public String convertAccountType() {
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}
