package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.model.CaseAccount;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.ClientAccountType;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.CaseAccountRepository;
import com.jamersc.springboot.financialhub.repository.ClientAccountRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
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

    @Autowired
    private CaseAccountRepository caseAccountRepository;

    @Autowired
    private UserRepository userRepository;

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
        CaseAccount caseAccount;
        ClientAccount account = new ClientAccount();
        account.setClient(clientAccount.getClient());
        account.setAccountTitle(clientAccount.getAccountTitle());
        account.setClientAccountType(ClientAccountType.CASE);
        User createdBy = userRepository.findByUsername(username);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        clientAccountRepository.save(account);

        if (clientAccount.getCaseAccount() != null) {
            caseAccount = new CaseAccount();
            caseAccount.setClientAccount(account);
            caseAccount.setCaseType(clientAccount.getCaseAccount().getCaseType());
            caseAccount.setCaseTitle(clientAccount.getAccountTitle());
            caseAccount.setDocketNo(clientAccount.getCaseAccount().getDocketNo());
            caseAccount.setNature(clientAccount.getCaseAccount().getNature());
            caseAccount.setCourt(clientAccount.getCaseAccount().getCourt());
            caseAccount.setBranch(clientAccount.getCaseAccount().getBranch());
            caseAccount.setJudge(clientAccount.getCaseAccount().getJudge());
            caseAccount.setCourtEmail(clientAccount.getCaseAccount().getCourtEmail());
            caseAccount.setProsecutor(clientAccount.getCaseAccount().getProsecutor());
            caseAccount.setProsecutorEmail(clientAccount.getCaseAccount().getProsecutorEmail());
            caseAccount.setProsecutorOffice(clientAccount.getCaseAccount().getProsecutorOffice());
            caseAccount.setOpposingParty(clientAccount.getCaseAccount().getOpposingParty());
            caseAccount.setOpposingCounsel(clientAccount.getCaseAccount().getOpposingCounsel());
            caseAccount.setCounselEmail(clientAccount.getCaseAccount().getCounselEmail());
            caseAccount.setStatus(clientAccount.getCaseAccount().getStatus());
            caseAccount.setStage(clientAccount.getCaseAccount().getStage());
            caseAccount.setStartDate(clientAccount.getCaseAccount().getStartDate());
            caseAccount.setEndDate(clientAccount.getCaseAccount().getEndDate());
            caseAccount.setCreatedBy(account.getCreatedBy());
            caseAccount.setUpdatedBy(account.getCreatedBy());
            caseAccountRepository.save(caseAccount);
        }
    }

    @Override
    public void deleteClientAccountById(Long id) {
        clientAccountRepository.deleteById(id);
    }
}
