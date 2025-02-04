package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.RegistrationType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactCompanyDto {

    private Long companyId;
    // private ContactDto contact; // one to one contact id
    private Long contactId; // Instead of ContactDto reference

    private String companyName;
    private RegistrationType registrationType;
    private String representativeName;
    private String representativeDesignation;
    private String mobileNumber;
    private String emailAddress;
    private String address;

}

