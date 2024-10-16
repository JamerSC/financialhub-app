package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.ClientAccountType;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientAccountDto {

        private Long clientAccountId;
        private ContactDto client; // one to many contact id
        private String accountTitle;
        private ClientAccountType clientAccountType;
        private CaseAccountDto caseAccount; // one to one client account
        private Long createdBy;
        private Date createdAt;
        private Long updatedBy;
        private Date updatedAt;
}
