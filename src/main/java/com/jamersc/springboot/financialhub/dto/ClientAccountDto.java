package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.ClientAccountType;
import lombok.*;

import java.util.Date;
import java.util.Set;

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
        //private Long caseAccountId;
        private ProjectAccountDto projectAccount; // one to one client account
        //private Long projectAccountId;
        private RetainerAccountDto retainerAccount; // one to one client account
        private Long retainerAccountId;
        private Set<PettyCashActivityDto> pettyCash;
        //private Set<Long> pettyCashActivityId;
        private LiquidationDto activity;
        //private Long ActivityId;

        private Long createdBy;
        private Date createdAt;
        private Long updatedBy;
        private Date updatedAt;

        public ClientAccountDto(Long clientAccountId, String accountTitle, ClientAccountType clientAccountType) {
        }

   /*     public String getClientDisplayName() {
                if (client != null) {
                        if (client.getIndividual() != null) {
                                return client.getIndividual().getFullName();
                        } else if (client.getCompany() != null) {
                                return client.getCompany().getCompanyName();
                        }
                }
                return "";
        }*/
}
