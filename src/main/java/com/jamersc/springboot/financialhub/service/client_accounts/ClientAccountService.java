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

    void saveClientCaseAccount(ClientAccountDto clientAccountDto, String username);

    void updateClientCaseAccount(ClientAccountDto clientAccountDto, String username);

   /* *** Project Account *** */

    void saveClientTransferOfTitleAccount(ClientAccountDto clientAccountDto, String username);

    void saveClientSettlementOfEstateAccount(ClientAccountDto clientAccountDto, String username);

    /* *** Retainer Account *** */

    void saveClientRetainerAccount(ClientAccountDto clientAccountDto, String username);

    void updateClientRetainerAccount(ClientAccountDto clientAccountDto, String username);

    void deleteClientAccountById(Long id);
}
