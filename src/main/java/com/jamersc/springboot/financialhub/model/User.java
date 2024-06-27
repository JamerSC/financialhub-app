package com.jamersc.springboot.financialhub.model;

public class User {

    private String fullName;
    private int cash;

    public User() {
    }

    public User(String fullName, int cash) {
        this.fullName = fullName;
        this.cash = cash;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
