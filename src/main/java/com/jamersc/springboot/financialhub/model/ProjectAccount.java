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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
