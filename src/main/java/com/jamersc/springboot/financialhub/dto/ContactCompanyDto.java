package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.contact.Contact;
import com.jamersc.springboot.financialhub.model.contact.RegistrationType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactCompanyDto {

    private Long companyId;
    private ContactDto contact; // one to one contact id
    private String companyName;
    private RegistrationType registrationType;
    private String representativeName;
    private String representativeDesignation;
    private String mobileNumber;
    private String emailAddress;
    private String address;

}

