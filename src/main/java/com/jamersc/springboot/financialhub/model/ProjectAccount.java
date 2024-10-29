package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client_project_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"clientAccount"})
public class ProjectAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_account_id")
    private ClientAccount clientAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_type")
    private ProjectType projectType;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_sub_type")
    private PropertySubType propertySubType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sec_sub_type")
    private SecSubType secSubType;

    @Column(name = "project_title")
    private String projectTitle;

    @Column(name = "title_no")
    private String titleNo;

    @Column(name = "tax_dec_no")
    private String taxDecNo;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "lot_area")
    private String lotArea;

    @Column(name = "location")
    private String location;

    @Column(name = "bir")
    private String bir;

    @Column(name = "rd")
    private String rd;

    @Column(name = "zonal_value")
    private String zonalValue;

    @Column(name = "purchase_price")
    private String purchasePrice;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "deceased")
    private String deceased;

    @Column(name = "heirs")
    private String heirs;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
