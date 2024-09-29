package com.jamersc.springboot.financialhub.model;

public enum CaseType {

    CIVIL,
    CRIMINAL,
    LABOR,
    ADMINISTRATIVE,
    ELECTION,
    SPECIAL_PROCEEDINGS,
    OTHERS;

    public String convertCaseType() {
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}
