package com.jamersc.springboot.financialhub.model;

import com.jamersc.springboot.financialhub.model.contact.Contact;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "cases")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Cases {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id")
    private Long caseId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private Contact client;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_by")
    private int updatedBy;

    //@Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
