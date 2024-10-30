package com.jamersc.springboot.financialhub.service.client_accounts;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.mapper.ClientAccountMapper;
import com.jamersc.springboot.financialhub.mapper.ContactMapper;
import com.jamersc.springboot.financialhub.model.*;
import com.jamersc.springboot.financialhub.repository.*;
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
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CaseAccountRepository caseAccountRepository;
    @Autowired
    private ProjectAccountRepository projectAccountRepository;
    @Autowired
    private RetainerAccountRepository retainerAccountRepository;

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
    public List<Long> getClientsWithRetainers() {
        return clientAccountRepository.findClientIdsWithRetainers();
    }

    @Override
    public List<ClientAccount> getAllProjectAccounts() {
        return clientAccountRepository.findByClientAccountType(ClientAccountType.PROJECT);
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

    /* *** Case Account *** */

    @Override
    public void saveClientCaseAccount(ClientAccountDto clientAccountDto, String username) {
        ClientAccount account;
        CaseAccount caseAccount;

        User createdBy = userRepository.findByUsername(username);

        account= new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(clientAccountDto.getClient()));
        account.setAccountTitle(clientAccountDto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.CASE);
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
            }
            account.setAccountTitle(clientAccountDto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating client's case account: " + account);

            clientAccountRepository.save(account);

            boolean caseAccountUpdated = false;

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
                logger.info("Updating case account details: " + caseAccount);
                caseAccountRepository.save(caseAccount);

                caseAccountUpdated = true;
            }
            if (caseAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }

    }

    /* *** Project Account *** */

    /* *** TRANSFER OF TITLE *** */
    @Override
    public void saveClientTransferOfTitleAccount(ClientAccountDto clientAccountDto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(clientAccountDto.getClient()));
        account.setAccountTitle(clientAccountDto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        clientAccountRepository.save(account);

        if (clientAccountDto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.PROPERTIES);
            projectAccount.setPropertySubType(PropertySubType.TRANSFER_OF_TITLE);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(clientAccountDto.getAccountTitle());
            projectAccount.setTitleNo(clientAccountDto.getProjectAccount().getTitleNo());
            projectAccount.setTaxDecNo(clientAccountDto.getProjectAccount().getTaxDecNo());
            projectAccount.setLotNo(clientAccountDto.getProjectAccount().getLotNo());
            projectAccount.setLotArea(clientAccountDto.getProjectAccount().getLotArea());
            projectAccount.setLocation(clientAccountDto.getProjectAccount().getLocation());
            projectAccount.setBir(clientAccountDto.getProjectAccount().getBir());
            projectAccount.setRd(clientAccountDto.getProjectAccount().getRd());
            projectAccount.setZonalValue(clientAccountDto.getProjectAccount().getZonalValue());
            projectAccount.setPurchasePrice(clientAccountDto.getProjectAccount().getPurchasePrice());
            projectAccount.setRemarks(clientAccountDto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(clientAccountDto.getProjectAccount().getStatus());

            logger.info("Saving transfer of title details" + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    /* *** SETTLEMENT OF ESTATE *** */
    @Override
    public void saveClientSettlementOfEstateAccount(ClientAccountDto clientAccountDto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(clientAccountDto.getClient()));
        account.setAccountTitle(clientAccountDto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        clientAccountRepository.save(account);

        if (clientAccountDto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.PROPERTIES);
            projectAccount.setPropertySubType(PropertySubType.SETTLEMENT_OF_ESTATE);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(clientAccountDto.getAccountTitle());
            projectAccount.setTitleNo(clientAccountDto.getProjectAccount().getTitleNo());
            projectAccount.setTaxDecNo(null);
            projectAccount.setLotNo(null);
            projectAccount.setLotArea(null);
            projectAccount.setLocation(null);
            projectAccount.setBir(clientAccountDto.getProjectAccount().getBir());
            projectAccount.setRd(clientAccountDto.getProjectAccount().getRd());
            projectAccount.setZonalValue(null);
            projectAccount.setPurchasePrice(null);
            projectAccount.setRemarks(clientAccountDto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(clientAccountDto.getProjectAccount().getDeceased());
            projectAccount.setHeirs(clientAccountDto.getProjectAccount().getHeirs());
            projectAccount.setAddress(clientAccountDto.getProjectAccount().getAddress());
            projectAccount.setStatus(clientAccountDto.getProjectAccount().getStatus());

            logger.info("Saving transfer of title details" + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }


    /* *** Retainer Account *** */

    @Override
    public void saveClientRetainerAccount(ClientAccountDto clientAccountDto, String username) {
        ClientAccount account;
        RetainerAccount retainerAccount;
        User createdBy = userRepository.findByUsername(username);

        account= new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(clientAccountDto.getClient()));
        account.setAccountTitle(clientAccountDto.getClient().getIndividual().getFullName());
        account.setClientAccountType(ClientAccountType.RETAINER);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving new client account: " + account);
        clientAccountRepository.save(account);

        if (clientAccountDto.getRetainerAccount() != null) {
            retainerAccount = new RetainerAccount();
            retainerAccount.setClientAccount(account);
            retainerAccount.setRetainerTitle(account.getClient().getIndividual().getFullName());
            retainerAccount.setStatus(clientAccountDto.getRetainerAccount().getStatus());
            retainerAccount.setStartDate(clientAccountDto.getRetainerAccount().getStartDate());
            retainerAccount.setEndDate(clientAccountDto.getRetainerAccount().getEndDate());
            logger.info("Saving retainer account details: " + retainerAccount);
            retainerAccountRepository.save(retainerAccount);
        }
    }

    @Override
    public void updateClientRetainerAccount(ClientAccountDto clientAccountDto, String username) {
        ClientAccount account;
        RetainerAccount retainerAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (clientAccountDto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(clientAccountDto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (clientAccountDto.getClient() != null) {
                Contact contact = ContactMapper.toContactEntity(clientAccountDto.getClient());
                Contact contactId = contactRepository.findById(contact.getContactId()).orElse(null);
                account.setClient(contactId);

                // Check if client has an individual or company before setting account title
                if (clientAccountDto.getClient().getIndividual() != null) {
                    account.setAccountTitle(clientAccountDto.getClient().getIndividual().getFullName());
                } else if (clientAccountDto.getClient().getCompany() != null) {
                    account.setAccountTitle(clientAccountDto.getClient().getCompany().getCompanyName());
                }
            }
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating client's retainer account: " + account);
            clientAccountRepository.save(account);

            boolean retainerAccountUpdated = false;

            if (clientAccountDto.getRetainerAccount() != null) {
                retainerAccount = retainerAccountRepository.findById(clientAccountDto.getRetainerAccount().getRetainerId())
                        .orElse(new RetainerAccount());
                retainerAccount.setClientAccount(account);

                // Check if client has an individual or company before setting retainer title
                if (clientAccountDto.getClient() != null) {
                    if (clientAccountDto.getClient().getIndividual() != null) {
                        retainerAccount.setRetainerTitle(clientAccountDto.getClient().getIndividual().getFullName());
                    } else if (clientAccountDto.getClient().getCompany() != null) {
                        retainerAccount.setRetainerTitle(clientAccountDto.getClient().getCompany().getCompanyName());
                    }
                }

                retainerAccount.setStatus(clientAccountDto.getRetainerAccount().getStatus());
                retainerAccount.setStartDate(clientAccountDto.getRetainerAccount().getStartDate());
                retainerAccount.setEndDate(clientAccountDto.getRetainerAccount().getEndDate());
                logger.info("Updating client's retainer account details: " + account);
                retainerAccountRepository.save(retainerAccount);

                retainerAccountUpdated = true;
            }

            if (retainerAccountUpdated) {
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
