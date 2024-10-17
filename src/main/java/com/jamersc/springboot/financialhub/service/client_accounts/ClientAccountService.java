package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;

import java.util.List;

public interface ClientAccountService {

    List<ClientAccount> getAllClientAccounts();

    ClientAccountDto getClientAccountById(Long id);

    void saveClientAccount(ClientAccountDto clientAccountDto, String username);

    void deleteClientAccountById(Long id);
}
