package com.jamersc.springboot.financialhub.model;

import lombok.Getter;

public enum Status {

    ACTIVE("Active"),
    ARCHIVE("Archive"),
    CLOSED("Closed");

    private final String statusType;

    Status(String statusType) {
        this.statusType = statusType;
    }

    public String displayStatus() {
        return statusType;
    }
}
