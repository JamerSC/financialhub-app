package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.ContactCategoryType;
import com.jamersc.springboot.financialhub.model.ContactType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactDto {

    private Long contactId;
    private ContactType contactType;
    private ContactCategoryType contactCategoryType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date engagementDate;
    private String bestChannelToContact;
    // Bidirectional relation
    private ContactIndividualDto individual; // one to one
    private ContactCompanyDto company; // one to one
    private ContactDetailsDto additionalDetails; //one to one
    private List<ClientAccountDto> clientAccounts = new ArrayList<>(); // one to many
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;

}
