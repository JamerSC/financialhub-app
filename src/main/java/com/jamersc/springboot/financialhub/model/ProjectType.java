package com.jamersc.springboot.financialhub.model;



public enum ProjectType {

    PROPERTIES("Properties"),
    BUSINESS("Business"),
    SEC("SEC");

    private final String projectType;

    ProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String displayProjectType() {
        return projectType;
    }
}
