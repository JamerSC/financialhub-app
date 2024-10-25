package com.jamersc.springboot.financialhub.model;

public enum ClientAccountType {

    CASE("Case"),
    RETAINER("Retainer"),
    PROJECT("Project");

    private final String clientAccountType;

    ClientAccountType(String clientAccountType){
        this.clientAccountType = clientAccountType;
    }

    public String displayClientAccountType() {
        return clientAccountType;
    }

}
