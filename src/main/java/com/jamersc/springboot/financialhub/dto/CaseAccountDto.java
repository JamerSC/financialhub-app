package com.jamersc.springboot.financialhub.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamersc.springboot.financialhub.model.CaseType;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.Status;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CaseAccountDto {

    private Long caseId;
    private ClientAccountDto clientAccount; // one to one ClientAccount
    private CaseType caseType;
    private String caseTitle;
    private String docketNo;
    private String nature;
    private String court;
    private String branch;
    private String judge;
    private String courtEmail;
    private String prosecutor;
    private String prosecutorOffice;
    private String prosecutorEmail;
    private String opposingParty;
    private String opposingCounsel;
    private String counselEmail;
    private Status status;
    private String stage;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
