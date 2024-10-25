package com.jamersc.springboot.financialhub.model;

public enum RegistrationType {
    CORPORATION("Corporation"),
    PARTNERSHIP("Partnership"),
    SINGLE_PROPRIETORSHIP("Single Proprietorship"),
    FOUNDATION("Foundation"),
    ASSOCIATION("Association"),
    OTHERS("Others");

    private final String registrationType;

    RegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String displayRegistrationType() {
        return registrationType;
    }
}
