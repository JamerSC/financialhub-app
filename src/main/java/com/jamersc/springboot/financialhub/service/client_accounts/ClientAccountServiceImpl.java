package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.mapper.ClientAccountMapper;
import com.jamersc.springboot.financialhub.mapper.ContactMapper;
import com.jamersc.springboot.financialhub.model.*;
import com.jamersc.springboot.financialhub.repository.CaseAccountRepository;
import com.jamersc.springboot.financialhub.repository.ClientAccountRepository;
import com.jamersc.springboot.financialhub.repository.ContactRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import com.jamersc.springboot.financialhub.service.bank.BankAccountServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public List<ClientAccount> getAllClientAccounts() {
        return clientAccountRepository.findAll();
    }

    // FIND ALL CLIENT ACCOUNT CASES
    @Override
    public List<ClientAccount> getAllCaseAccounts() {
        return clientAccountRepository.findByClientAccountType(ClientAccountType.CASE);
    }

    @Override
    public List<ClientAccount> getAllRetainerAccounts() {
        return clientAccountRepository.findByClientAccountType(ClientAccountType.RETAINER);
    }

    @Override
    public List<ClientAccount> getAllProjectAccounts() {
        return null;
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
    public void saveClientCaseAccount(ClientAccountDto clientAccountDto, String username) {
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
            logger.info("Saving case account details: " + caseAccount);
            caseAccountRepository.save(caseAccount);
        }
    }

    @Override
    public void updateClientCaseAccount(ClientAccountDto clientAccountDto, String username) {
        ClientAccount account;
        CaseAccount caseAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (clientAccountDto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(clientAccountDto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (clientAccountDto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(clientAccountDto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId()).orElse(null);
                account.setClient(contactId);
                //account.setClient(ContactMapper.toContactEntity(clientAccountDto.getClient()));
            }
            account.setAccountTitle(clientAccountDto.getAccountTitle());
            //account.setClientAccountType(ClientAccountType.CASE);
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating client's case account: " + account);

            clientAccountRepository.save(account);

            boolean clientAccountUpdated = false;

            if (clientAccountDto.getCaseAccount() != null) {
                caseAccount = caseAccountRepository.findById(clientAccountDto.getCaseAccount().getCaseId())
                        .orElse(new CaseAccount());
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
                logger.info("Updating account details: " + caseAccount);
                caseAccountRepository.save(caseAccount);

                clientAccountUpdated = true;
            }
            if (clientAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }

    }

    @Override
    public void deleteClientAccountById(Long id) {
        clientAccountRepository.deleteById(id);
    }
}
