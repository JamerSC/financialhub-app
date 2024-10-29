package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectAccountDto {

    private Long projectId;
    private ClientAccountDto clientAccount;
    private ProjectType projectType;
    private PropertySubType propertySubType;
    private SecSubType secSubType;
    private String projectTitle;
    private String titleNo;
    private String taxDecNo;
    private String lotNo;
    private String lotArea;
    private String location;
    private String bir;
    private String rd;
    private String zonalValue;
    private String purchasePrice;
    private String remarks;
    private String deceased;
    private String heirs;
    private String address;
    private Status status;
}
