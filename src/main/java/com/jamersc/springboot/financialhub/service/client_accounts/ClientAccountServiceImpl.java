package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.repository.ClientAccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ClientAccountServiceImpl implements ClientAccountService{

    @Autowired
    private ClientAccountRepository clientAccountRepository;

    @Override
    public List<ClientAccount> getAllClientAccounts() {
        return clientAccountRepository.findAll();
    }

    @Override
    public ClientAccount getClientAccountById(Long id) {
        return clientAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client Account ID not found."));
    }

    @Override
    public void saveClientAccount(ClientAccount clientAccount, String username) {
        clientAccountRepository.save(clientAccount);
    }

    @Override
    public void deleteClientAccountById(Long id) {
        clientAccountRepository.deleteById(id);
    }
}
