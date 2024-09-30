package com.jamersc.springboot.financialhub.model;

public enum Status {
    OPEN,
    IN_PROGRESS,
    PENDING,
    COMPLETED,
    CLOSED;

    public String convertStatus() {
        String lowerCaseName = name().toLowerCase();
        return lowerCaseName.substring(0, 1).toUpperCase() + lowerCaseName.substring(1);
    }
}
