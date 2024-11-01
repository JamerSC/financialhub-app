package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.RetainerAccount;

import java.util.List;

public interface ClientAccountService {

    List<ClientAccount> getAllClientAccounts();

    List<ClientAccount> getAllCaseAccounts();

    List<ClientAccount> getAllRetainerAccounts();

    List<Long> getClientsWithRetainers();

    List<ClientAccount> getAllProjectAccounts();

    ClientAccountDto getClientAccountById(Long id);

    /* *** Case Account *** */

    void saveClientCaseAccount(ClientAccountDto dto, String username);

    void updateClientCaseAccount(ClientAccountDto dto, String username);

   /* *** Project Account *** */

    void saveClientTransferOfTitleAccount(ClientAccountDto dto, String username);

    void updateClientTransferOfTitleAccount(ClientAccountDto dto, String username);

    void saveClientSettlementOfEstateAccount(ClientAccountDto dto, String username);

    void updateClientSettlementOfEstateAccount(ClientAccountDto dto, String username);

    void saveClientTitleAnnotationAccount(ClientAccountDto dto, String username);

    void updateClientTitleAnnotationAccount(ClientAccountDto dto, String username);

    void saveClientTitleOtherAccount(ClientAccountDto dto, String username);

    void updateClientTitleOtherAccount(ClientAccountDto dto, String username);

    void saveClientBusinessRegistrationAccount(ClientAccountDto dto, String username);

    void updateClientBusinessRegistrationAccount(ClientAccountDto dto, String username);

    void saveClientBusinessRenewalAccount(ClientAccountDto dto, String username);

    void updateClientBusinessRenewalAccount(ClientAccountDto dto, String username);

    void saveClientBusinessClosureAccount(ClientAccountDto dto, String username);

    void updateClientBusinessClosureAccount(ClientAccountDto dto, String username);

    void saveClientBusinessOtherAccount(ClientAccountDto dto, String username);

    void updateClientBusinessOtherAccount(ClientAccountDto dto, String username);

    void saveClientSecRegistrationAccount(ClientAccountDto dto, String username);

    void updateClientSecRegistrationAccount(ClientAccountDto dto, String username);


    void saveClientSecAmendmentAccount(ClientAccountDto dto, String username);

    void updateClientSecAmendmentAccount(ClientAccountDto dto, String username);

    void saveClientSecStockIncreaseAccount(ClientAccountDto dto, String username);

    void updateClientSecStockIncreaseAccount(ClientAccountDto dto, String username);

    /* *** Retainer Account *** */

    void saveClientRetainerAccount(ClientAccountDto dto, String username);

    void updateClientRetainerAccount(ClientAccountDto dto, String username);

    void deleteClientAccountById(Long id);
}
