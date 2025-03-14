package com.jamersc.springboot.financialhub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "client_case_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"clientAccount"})
public class CaseAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id")
    private Long caseId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_account_id")
    private ClientAccount clientAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "case_type")
    private CaseType caseType;

    @Column(name = "case_title")
    private String caseTitle;

    @Column(name = "docket_no")
    private String docketNo;

    @Column(name = "nature")
    private String nature;

    @Column(name = "court")
    private String court;

    @Column(name = "branch")
    private String branch;

    @Column(name = "judge")
    private String judge;

    @Column(name = "court_email")
    private String courtEmail;

    @Column(name = "prosecutor")
    private String prosecutor;

    @Column(name = "prosecutor_office")
    private String prosecutorOffice;

    @Column(name = "prosecutor_email")
    private String prosecutorEmail;

    @Column(name = "opposing_party")
    private String opposingParty;

    @Column(name = "opposing_counsel")
    private String opposingCounsel;

    @Column(name = "counsel_email")
    private String counselEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "stage")
    private String stage;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;
}
