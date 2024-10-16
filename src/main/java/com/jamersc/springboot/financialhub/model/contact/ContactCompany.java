package com.jamersc.springboot.financialhub.model.contact;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_company")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "contact")
public class ContactCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @OneToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @Column(name = "company_name")
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "registration_type")
    private RegistrationType registrationType;

    @Column(name = "representative_name")
    private String representativeName;

    @Column(name = "representative_designation")
    private String representativeDesignation;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

}

