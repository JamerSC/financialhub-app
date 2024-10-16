package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.contact.Contact;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactIndividualDto {

    private Long individualId;
    private ContactDto contact; // @OneToOne contact id
    private String title;
    private String lastName;
    private String firstName;
    private String middleName;
    private String suffix;
    private String mobileNumber;
    private String emailAddress;
    private String address;
}
