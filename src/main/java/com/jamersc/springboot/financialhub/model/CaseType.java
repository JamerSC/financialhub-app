package com.jamersc.springboot.financialhub.model;

public enum CaseType {

    CIVIL("Civil"),
    CRIMINAL("Criminal"),
    LABOR("Labor"),
    ADMINISTRATIVE("Administrative"),
    ELECTION("Election"),
    SPECIAL_PROCEEDINGS("Special Proceedings"),
    OTHERS("Others");

    private final String caseType;

    CaseType(String caseType) {
        this.caseType = caseType;
    }

    public String displayCaseType() {
        return caseType;
    }

    /*public String convertCaseType() {
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }*/
}
