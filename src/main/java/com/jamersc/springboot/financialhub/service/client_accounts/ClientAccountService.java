package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.model.ClientAccount;

import java.util.List;

public interface ClientAccountService {

    List<ClientAccount> getAllClientAccounts();

    ClientAccount getClientAccountById(Long id);

    void saveClientAccount(ClientAccount clientAccount, String username);

    void deleteClientAccountById(Long id);
}
