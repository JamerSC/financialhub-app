package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.mapper.ClientAccountMapper;
import com.jamersc.springboot.financialhub.mapper.ContactMapper;
import com.jamersc.springboot.financialhub.model.CaseAccount;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.ClientAccountType;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.CaseAccountRepository;
import com.jamersc.springboot.financialhub.repository.ClientAccountRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import com.jamersc.springboot.financialhub.service.bank.BankAccountServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ClientAccountServiceImpl implements ClientAccountService{

    private static final Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);
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
    public ClientAccountDto getClientAccountById(Long id) {
        ClientAccount clientAccount = clientAccountRepository.findById(id).orElse(null);
        if (clientAccount != null) {
            ClientAccountDto clientAccountDto = ClientAccountMapper.toClientAccountDto(clientAccount);
            logger.info("Finding Client Account ID: " + clientAccountDto);
            return clientAccountDto;
        }
        throw new RuntimeException("Client Account ID not found!");
    }

    @Override
    public void saveClientAccount(ClientAccountDto clientAccountDto, String username) {
        CaseAccount caseAccount;
        ClientAccount account;


        account= new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(clientAccountDto.getClient()));
        account.setAccountTitle(clientAccountDto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.CASE);
        User createdBy = userRepository.findByUsername(username);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving new client account: " + account);
        clientAccountRepository.save(account);

        // Update
        if (clientAccountDto.getCaseAccount() != null) {
            caseAccount = new CaseAccount();
            caseAccount.setClientAccount(account);
            caseAccount.setCaseType(clientAccountDto.getCaseAccount().getCaseType());
            caseAccount.setCaseTitle(clientAccountDto.getAccountTitle());
            caseAccount.setDocketNo(clientAccountDto.getCaseAccount().getDocketNo());
            caseAccount.setNature(clientAccountDto.getCaseAccount().getNature());
            caseAccount.setCourt(clientAccountDto.getCaseAccount().getCourt());
            caseAccount.setBranch(clientAccountDto.getCaseAccount().getBranch());
            caseAccount.setJudge(clientAccountDto.getCaseAccount().getJudge());
            caseAccount.setCourtEmail(clientAccountDto.getCaseAccount().getCourtEmail());
            caseAccount.setProsecutor(clientAccountDto.getCaseAccount().getProsecutor());
            caseAccount.setProsecutorEmail(clientAccountDto.getCaseAccount().getProsecutorEmail());
            caseAccount.setProsecutorOffice(clientAccountDto.getCaseAccount().getProsecutorOffice());
            caseAccount.setOpposingParty(clientAccountDto.getCaseAccount().getOpposingParty());
            caseAccount.setOpposingCounsel(clientAccountDto.getCaseAccount().getOpposingCounsel());
            caseAccount.setCounselEmail(clientAccountDto.getCaseAccount().getCounselEmail());
            caseAccount.setStatus(clientAccountDto.getCaseAccount().getStatus());
            caseAccount.setStage(clientAccountDto.getCaseAccount().getStage());
            caseAccount.setStartDate(clientAccountDto.getCaseAccount().getStartDate());
            caseAccount.setEndDate(clientAccountDto.getCaseAccount().getEndDate());
            caseAccount.setCreatedBy(account.getCreatedBy());
            caseAccount.setUpdatedBy(account.getCreatedBy());
            logger.info("Saving case account details: " + caseAccount);
            caseAccountRepository.save(caseAccount);
        }
    }

    @Override
    public void deleteClientAccountById(Long id) {
        clientAccountRepository.deleteById(id);
    }
}
