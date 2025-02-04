package com.jamersc.springboot.financialhub.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactDetailsDto {

    private Long detailId;
    //private ContactDto contact; // one to one contact id
    private Long contactId; // Instead of ContactDto reference
    private String designationFor;
    private String bankName;
    private String accountNo;
}
