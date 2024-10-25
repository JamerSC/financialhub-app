package com.jamersc.springboot.financialhub.model;



public enum ProjectType {

    PROPERTIES("Properties"),
    BUSINESS_REGISTRATION("Business Registration"),
    BUSINESS_CLOSURE("Business Closure"),
    SEC("Securities and Exchange Commission (SEC)");

    private final String projectType;

    ProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String displayProjectType() {
        return projectType;
    }
}
