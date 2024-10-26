package com.jamersc.springboot.financialhub.model;

import lombok.Getter;

public enum Status {
    OPEN("Open"),
    IN_PROGRESS("In Progress"),
    PENDING("Pending"),
    COMPLETED("Completed"),
    CLOSED("Closed");

    private final String statusType;

    Status(String statusType) {
        this.statusType = statusType;
    }

    public String displayStatus() {
        return statusType;
    }
}
