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
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ClientAccountServiceImpl implements ClientAccountService{

    private static final Logger logger = LoggerFactory.getLogger(ClientAccountServiceImpl.class);
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
    public List<ClientAccountDto> getAllClientAccounts() {
        logger.info("Find all client accounts");
        return clientAccountRepository.findAll().stream()
                .map(ClientAccountMapper::toClientAccountDto)
                .collect(Collectors.toList());
    }

    // FIND ALL CLIENT ACCOUNT CASES
    @Override
    public List<ClientAccountDto> getAllCaseAccounts() {
        logger.info("Get all client case accounts");
        return clientAccountRepository.findByClientAccountType(ClientAccountType.CASE)
                .stream().map(ClientAccountMapper::toClientAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClientAccountDto> getAllRetainerAccounts() {
        logger.info("Get all client retainer accounts");
        return clientAccountRepository.findByClientAccountType(ClientAccountType.RETAINER)
                .stream().map(ClientAccountMapper::toClientAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getClientsWithRetainers() {
        return clientAccountRepository.findClientIdsWithRetainers();
    }

    @Override
    public List<ClientAccountDto> getAllProjectAccounts() {
        logger.info("Get all client project accounts");
        return clientAccountRepository.findByClientAccountType(ClientAccountType.PROJECT)
                .stream().map(ClientAccountMapper::toClientAccountDto)
                .collect(Collectors.toList());
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
    public void saveClientCaseAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        CaseAccount caseAccount;

        User createdBy = userRepository.findByUsername(username);

        account= new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.CASE);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving new client account: " + account);
        clientAccountRepository.save(account);

        // Update
        if (dto.getCaseAccount() != null) {
            caseAccount = new CaseAccount();
            caseAccount.setClientAccount(account);
            caseAccount.setCaseType(dto.getCaseAccount().getCaseType());
            caseAccount.setCaseTitle(dto.getAccountTitle());
            caseAccount.setDocketNo(dto.getCaseAccount().getDocketNo());
            caseAccount.setNature(dto.getCaseAccount().getNature());
            caseAccount.setCourt(dto.getCaseAccount().getCourt());
            caseAccount.setBranch(dto.getCaseAccount().getBranch());
            caseAccount.setJudge(dto.getCaseAccount().getJudge());
            caseAccount.setCourtEmail(dto.getCaseAccount().getCourtEmail());
            caseAccount.setProsecutor(dto.getCaseAccount().getProsecutor());
            caseAccount.setProsecutorEmail(dto.getCaseAccount().getProsecutorEmail());
            caseAccount.setProsecutorOffice(dto.getCaseAccount().getProsecutorOffice());
            caseAccount.setOpposingParty(dto.getCaseAccount().getOpposingParty());
            caseAccount.setOpposingCounsel(dto.getCaseAccount().getOpposingCounsel());
            caseAccount.setCounselEmail(dto.getCaseAccount().getCounselEmail());
            caseAccount.setStatus(dto.getCaseAccount().getStatus());
            caseAccount.setStage(dto.getCaseAccount().getStage());
            caseAccount.setStartDate(dto.getCaseAccount().getStartDate());
            caseAccount.setEndDate(dto.getCaseAccount().getEndDate());
            logger.info("Saving case account details: " + caseAccount);
            caseAccountRepository.save(caseAccount);
        }
    }

    @Override
    public void updateClientCaseAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        CaseAccount caseAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId()).orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating client's case account: " + account);

            clientAccountRepository.save(account);

            boolean caseAccountUpdated = false;

            if (dto.getCaseAccount() != null) {
                caseAccount = caseAccountRepository.findById(dto.getCaseAccount().getCaseId())
                        .orElse(new CaseAccount());
                caseAccount.setClientAccount(account);
                caseAccount.setCaseType(dto.getCaseAccount().getCaseType());
                caseAccount.setCaseTitle(dto.getAccountTitle());
                caseAccount.setDocketNo(dto.getCaseAccount().getDocketNo());
                caseAccount.setNature(dto.getCaseAccount().getNature());
                caseAccount.setCourt(dto.getCaseAccount().getCourt());
                caseAccount.setBranch(dto.getCaseAccount().getBranch());
                caseAccount.setJudge(dto.getCaseAccount().getJudge());
                caseAccount.setCourtEmail(dto.getCaseAccount().getCourtEmail());
                caseAccount.setProsecutor(dto.getCaseAccount().getProsecutor());
                caseAccount.setProsecutorEmail(dto.getCaseAccount().getProsecutorEmail());
                caseAccount.setProsecutorOffice(dto.getCaseAccount().getProsecutorOffice());
                caseAccount.setOpposingParty(dto.getCaseAccount().getOpposingParty());
                caseAccount.setOpposingCounsel(dto.getCaseAccount().getOpposingCounsel());
                caseAccount.setCounselEmail(dto.getCaseAccount().getCounselEmail());
                caseAccount.setStatus(dto.getCaseAccount().getStatus());
                caseAccount.setStage(dto.getCaseAccount().getStage());
                caseAccount.setStartDate(dto.getCaseAccount().getStartDate());
                caseAccount.setEndDate(dto.getCaseAccount().getEndDate());
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
    public void saveClientTransferOfTitleAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account transfer of title: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.PROPERTIES);
            projectAccount.setPropertySubType(PropertySubType.TRANSFER_OF_TITLE);
            projectAccount.setBusinessSubType(null);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(dto.getProjectAccount().getTitleNo());
            projectAccount.setTaxDecNo(dto.getProjectAccount().getTaxDecNo());
            projectAccount.setLotNo(dto.getProjectAccount().getLotNo());
            projectAccount.setLotArea(dto.getProjectAccount().getLotArea());
            projectAccount.setLocation(dto.getProjectAccount().getLocation());
            projectAccount.setBir(dto.getProjectAccount().getBir());
            projectAccount.setRd(dto.getProjectAccount().getRd());
            projectAccount.setZonalValue(dto.getProjectAccount().getZonalValue());
            projectAccount.setPurchasePrice(dto.getProjectAccount().getPurchasePrice());
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving transfer of title details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientTransferOfTitleAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account transfer of title: " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setTitleNo(dto.getProjectAccount().getTitleNo());
                projectAccount.setTaxDecNo(dto.getProjectAccount().getTaxDecNo());
                projectAccount.setLotNo(dto.getProjectAccount().getLotNo());
                projectAccount.setLotArea(dto.getProjectAccount().getLotArea());
                projectAccount.setLocation(dto.getProjectAccount().getLocation());
                projectAccount.setBir(dto.getProjectAccount().getBir());
                projectAccount.setRd(dto.getProjectAccount().getRd());
                projectAccount.setZonalValue(dto.getProjectAccount().getZonalValue());
                projectAccount.setPurchasePrice(dto.getProjectAccount().getPurchasePrice());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating transfer of title details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* *** SETTLEMENT OF ESTATE *** */
    @Override
    public void saveClientSettlementOfEstateAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account settlement of estate: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.PROPERTIES);
            projectAccount.setPropertySubType(PropertySubType.SETTLEMENT_OF_ESTATE);
            projectAccount.setBusinessSubType(null);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(dto.getProjectAccount().getTitleNo());
            projectAccount.setTaxDecNo(null);
            projectAccount.setLotNo(null);
            projectAccount.setLotArea(null);
            projectAccount.setLocation(null);
            projectAccount.setBir(dto.getProjectAccount().getBir());
            projectAccount.setRd(dto.getProjectAccount().getRd());
            projectAccount.setZonalValue(null);
            projectAccount.setPurchasePrice(null);
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(dto.getProjectAccount().getDeceased());
            projectAccount.setHeirs(dto.getProjectAccount().getHeirs());
            projectAccount.setAddress(dto.getProjectAccount().getAddress());
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving settlement of estate details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientSettlementOfEstateAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account settlement of estate : " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setTitleNo(dto.getProjectAccount().getTitleNo());
                projectAccount.setBir(dto.getProjectAccount().getBir());
                projectAccount.setRd(dto.getProjectAccount().getRd());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setDeceased(dto.getProjectAccount().getDeceased());
                projectAccount.setHeirs(dto.getProjectAccount().getHeirs());
                projectAccount.setAddress(dto.getProjectAccount().getAddress());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating settlement of estate details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* *** ANNOTATION *** */
    @Override
    public void saveClientTitleAnnotationAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account title annotation: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.PROPERTIES);
            projectAccount.setPropertySubType(PropertySubType.ANNOTATION);
            projectAccount.setBusinessSubType(null);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(dto.getProjectAccount().getTitleNo());
            projectAccount.setTaxDecNo(dto.getProjectAccount().getTaxDecNo());
            projectAccount.setLotNo(dto.getProjectAccount().getLotNo());
            projectAccount.setLotArea(dto.getProjectAccount().getLotArea());
            projectAccount.setLocation(dto.getProjectAccount().getLocation());
            projectAccount.setBir(dto.getProjectAccount().getBir());
            projectAccount.setRd(dto.getProjectAccount().getRd());
            projectAccount.setZonalValue(dto.getProjectAccount().getZonalValue());
            projectAccount.setPurchasePrice(dto.getProjectAccount().getPurchasePrice());
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving annotation details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientTitleAnnotationAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account title annotation: " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setTitleNo(dto.getProjectAccount().getTitleNo());
                projectAccount.setTaxDecNo(dto.getProjectAccount().getTaxDecNo());
                projectAccount.setLotNo(dto.getProjectAccount().getLotNo());
                projectAccount.setLotArea(dto.getProjectAccount().getLotArea());
                projectAccount.setLocation(dto.getProjectAccount().getLocation());
                projectAccount.setBir(dto.getProjectAccount().getBir());
                projectAccount.setRd(dto.getProjectAccount().getRd());
                projectAccount.setZonalValue(dto.getProjectAccount().getZonalValue());
                projectAccount.setPurchasePrice(dto.getProjectAccount().getPurchasePrice());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating title annotation details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* *** TITLE OTHER TRANSACTION *** */
    @Override
    public void saveClientTitleOtherAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account title other transaction: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.PROPERTIES);
            projectAccount.setPropertySubType(PropertySubType.OTHERS);
            projectAccount.setBusinessSubType(null);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(dto.getProjectAccount().getTitleNo());
            projectAccount.setTaxDecNo(dto.getProjectAccount().getTaxDecNo());
            projectAccount.setLotNo(dto.getProjectAccount().getLotNo());
            projectAccount.setLotArea(dto.getProjectAccount().getLotArea());
            projectAccount.setLocation(dto.getProjectAccount().getLocation());
            projectAccount.setBir(dto.getProjectAccount().getBir());
            projectAccount.setRd(dto.getProjectAccount().getRd());
            projectAccount.setZonalValue(dto.getProjectAccount().getZonalValue());
            projectAccount.setPurchasePrice(dto.getProjectAccount().getPurchasePrice());
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving other title details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientTitleOtherAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account title other process   : " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setTitleNo(dto.getProjectAccount().getTitleNo());
                projectAccount.setTaxDecNo(dto.getProjectAccount().getTaxDecNo());
                projectAccount.setLotNo(dto.getProjectAccount().getLotNo());
                projectAccount.setLotArea(dto.getProjectAccount().getLotArea());
                projectAccount.setLocation(dto.getProjectAccount().getLocation());
                projectAccount.setBir(dto.getProjectAccount().getBir());
                projectAccount.setRd(dto.getProjectAccount().getRd());
                projectAccount.setZonalValue(dto.getProjectAccount().getZonalValue());
                projectAccount.setPurchasePrice(dto.getProjectAccount().getPurchasePrice());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating title other process details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* *** BUSINESS REGISTRATION *** */
    @Override
    public void saveClientBusinessRegistrationAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account business registration: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.BUSINESS);
            projectAccount.setPropertySubType(null);
            projectAccount.setBusinessSubType(BusinessSubType.BUSINESS_REGISTRATION);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(null);
            projectAccount.setTaxDecNo(null);
            projectAccount.setLotNo(null);
            projectAccount.setLotArea(null);
            projectAccount.setLocation(null);
            projectAccount.setBir(null);
            projectAccount.setRd(null);
            projectAccount.setZonalValue(null);
            projectAccount.setPurchasePrice(null);
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving business registration details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientBusinessRegistrationAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account business reg: " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating business reg details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* *** BUSINESS RENEWAL *** */
    @Override
    public void saveClientBusinessRenewalAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account business renewal: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.BUSINESS);
            projectAccount.setPropertySubType(null);
            projectAccount.setBusinessSubType(BusinessSubType.BUSINESS_RENEWAL);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(null);
            projectAccount.setTaxDecNo(null);
            projectAccount.setLotNo(null);
            projectAccount.setLotArea(null);
            projectAccount.setLocation(null);
            projectAccount.setBir(null);
            projectAccount.setRd(null);
            projectAccount.setZonalValue(null);
            projectAccount.setPurchasePrice(null);
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving business renewal details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientBusinessRenewalAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account business renewal: " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating business renewal details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* *** BUSINESS CLOSURE *** */
    @Override
    public void saveClientBusinessClosureAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account business closure: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.BUSINESS);
            projectAccount.setPropertySubType(null);
            projectAccount.setBusinessSubType(BusinessSubType.BUSINESS_CLOSURE);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(null);
            projectAccount.setTaxDecNo(null);
            projectAccount.setLotNo(null);
            projectAccount.setLotArea(null);
            projectAccount.setLocation(null);
            projectAccount.setBir(null);
            projectAccount.setRd(null);
            projectAccount.setZonalValue(null);
            projectAccount.setPurchasePrice(null);
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving business closure details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientBusinessClosureAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account business closure: " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating business closure details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* *** BUSINESS OTHER TRANSACTION *** */
    @Override
    public void saveClientBusinessOtherAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account business others: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.BUSINESS);
            projectAccount.setPropertySubType(null);
            projectAccount.setBusinessSubType(BusinessSubType.OTHERS);
            projectAccount.setSecSubType(null);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(null);
            projectAccount.setTaxDecNo(null);
            projectAccount.setLotNo(null);
            projectAccount.setLotArea(null);
            projectAccount.setLocation(null);
            projectAccount.setBir(null);
            projectAccount.setRd(null);
            projectAccount.setZonalValue(null);
            projectAccount.setPurchasePrice(null);
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving business other details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientBusinessOtherAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account business other process: " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating business other process details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* ** SEC REGISTRATION ** */
    @Override
    public void saveClientSecRegistrationAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account sec registration: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.SEC);
            projectAccount.setPropertySubType(null);
            projectAccount.setBusinessSubType(null);
            projectAccount.setSecSubType(SecSubType.SEC_REGISTRATION);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(null);
            projectAccount.setTaxDecNo(null);
            projectAccount.setLotNo(null);
            projectAccount.setLotArea(null);
            projectAccount.setLocation(null);
            projectAccount.setBir(null);
            projectAccount.setRd(null);
            projectAccount.setZonalValue(null);
            projectAccount.setPurchasePrice(null);
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving sec registration details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientSecRegistrationAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account sec registration: " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating sec registration details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* ** SEC AMENDMENT ** */
    @Override
    public void saveClientSecAmendmentAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account sec amendment: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.SEC);
            projectAccount.setPropertySubType(null);
            projectAccount.setBusinessSubType(null);
            projectAccount.setSecSubType(SecSubType.AMENDMENT_OF_ARTICLES_OF_INCORPORATION);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(null);
            projectAccount.setTaxDecNo(null);
            projectAccount.setLotNo(null);
            projectAccount.setLotArea(null);
            projectAccount.setLocation(null);
            projectAccount.setBir(null);
            projectAccount.setRd(null);
            projectAccount.setZonalValue(null);
            projectAccount.setPurchasePrice(null);
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving sec amendment details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientSecAmendmentAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account sec amendment of articles: " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating sec amendment of articles details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* ** SEC STOCK INCREASE ** */
    @Override
    public void saveClientSecStockIncreaseAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User createdBy = userRepository.findByUsername(username);

        account = new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getAccountTitle());
        account.setClientAccountType(ClientAccountType.PROJECT);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving project account sec stock increase: " + account);
        clientAccountRepository.save(account);

        if (dto.getProjectAccount() != null) {
            projectAccount = new ProjectAccount();
            projectAccount.setClientAccount(account);
            projectAccount.setProjectType(ProjectType.SEC);
            projectAccount.setPropertySubType(null);
            projectAccount.setBusinessSubType(null);
            projectAccount.setSecSubType(SecSubType.INCREASE_IN_AUTHORIZED_CAPITAL_STOCK);
            projectAccount.setProjectTitle(dto.getAccountTitle());
            projectAccount.setTitleNo(null);
            projectAccount.setTaxDecNo(null);
            projectAccount.setLotNo(null);
            projectAccount.setLotArea(null);
            projectAccount.setLocation(null);
            projectAccount.setBir(null);
            projectAccount.setRd(null);
            projectAccount.setZonalValue(null);
            projectAccount.setPurchasePrice(null);
            projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
            projectAccount.setDeceased(null);
            projectAccount.setHeirs(null);
            projectAccount.setAddress(null);
            projectAccount.setStatus(dto.getProjectAccount().getStatus());

            logger.info("Saving sec stock increase details: " + projectAccount);
            projectAccountRepository.save(projectAccount);
        }
    }

    @Override
    public void updateClientSecStockIncreaseAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        ProjectAccount projectAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact client = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(client.getContactId())
                        .orElse(null);
                account.setClient(contactId);
            }
            account.setAccountTitle(dto.getAccountTitle());
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating project account sec stock increase: " + account);
            clientAccountRepository.save(account);

            boolean projectAccountUpdated = false;

            if (dto.getProjectAccount() != null) {
                projectAccount = projectAccountRepository.findById(dto.getProjectAccount().getProjectId())
                        .orElse(new ProjectAccount());
                projectAccount.setClientAccount(account);
                projectAccount.setProjectTitle(dto.getAccountTitle());
                projectAccount.setRemarks(dto.getProjectAccount().getRemarks());
                projectAccount.setStatus(dto.getProjectAccount().getStatus());

                logger.info("Updating sec stock increase details: " + projectAccount);
                projectAccountRepository.save(projectAccount);

                projectAccountUpdated = true;
            }
            if (projectAccountUpdated) {
                if (updatedBy != null) {
                    account.setUpdatedBy(updatedBy.getId());
                }
                account.setUpdatedAt(new Date());
                clientAccountRepository.save(account);
            }
        }
    }

    /* *** Retainer Account *** */

    @Override
    public void saveClientRetainerAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        RetainerAccount retainerAccount;
        User createdBy = userRepository.findByUsername(username);

        account= new ClientAccount();
        account.setClient(ContactMapper.toContactEntity(dto.getClient()));
        account.setAccountTitle(dto.getClient().getIndividual().getFullName());
        account.setClientAccountType(ClientAccountType.RETAINER);
        if (createdBy != null) {
            account.setCreatedBy(createdBy.getId());
            account.setUpdatedBy(createdBy.getId());
        }
        logger.info("Saving new client account: " + account);
        clientAccountRepository.save(account);

        if (dto.getRetainerAccount() != null) {
            retainerAccount = new RetainerAccount();
            retainerAccount.setClientAccount(account);
            retainerAccount.setRetainerTitle(account.getClient().getIndividual().getFullName());
            retainerAccount.setStatus(dto.getRetainerAccount().getStatus());
            retainerAccount.setStartDate(dto.getRetainerAccount().getStartDate());
            retainerAccount.setEndDate(dto.getRetainerAccount().getEndDate());
            logger.info("Saving retainer account details: " + retainerAccount);
            retainerAccountRepository.save(retainerAccount);
        }
    }

    @Override
    public void updateClientRetainerAccount(ClientAccountDto dto, String username) {
        ClientAccount account;
        RetainerAccount retainerAccount;

        User updatedBy = userRepository.findByUsername(username);

        if (dto.getClientAccountId() != null) {
            account = clientAccountRepository.findById(dto.getClientAccountId())
                    .orElse(new ClientAccount());
            if (dto.getClient() != null) {
                Contact contact = ContactMapper.toContactEntity(dto.getClient());
                Contact contactId = contactRepository.findById(contact.getContactId()).orElse(null);
                account.setClient(contactId);

                // Check if client has an individual or company before setting account title
                if (dto.getClient().getIndividual() != null) {
                    account.setAccountTitle(dto.getClient().getIndividual().getFullName());
                } else if (dto.getClient().getCompany() != null) {
                    account.setAccountTitle(dto.getClient().getCompany().getCompanyName());
                }
            }
            if (updatedBy != null) {
                account.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updating client's retainer account: " + account);
            clientAccountRepository.save(account);

            boolean retainerAccountUpdated = false;

            if (dto.getRetainerAccount() != null) {
                retainerAccount = retainerAccountRepository.findById(dto.getRetainerAccount().getRetainerId())
                        .orElse(new RetainerAccount());
                retainerAccount.setClientAccount(account);

                // Check if client has an individual or company before setting retainer title
                if (dto.getClient() != null) {
                    if (dto.getClient().getIndividual() != null) {
                        retainerAccount.setRetainerTitle(dto.getClient().getIndividual().getFullName());
                    } else if (dto.getClient().getCompany() != null) {
                        retainerAccount.setRetainerTitle(dto.getClient().getCompany().getCompanyName());
                    }
                }

                retainerAccount.setStatus(dto.getRetainerAccount().getStatus());
                retainerAccount.setStartDate(dto.getRetainerAccount().getStartDate());
                retainerAccount.setEndDate(dto.getRetainerAccount().getEndDate());
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
