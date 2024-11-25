package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.dto.BankDto;
import com.jamersc.springboot.financialhub.mapper.BankMapper;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.model.Bank;
import com.jamersc.springboot.financialhub.repository.BankRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BankServiceImpl implements BankService{

    //private static final Logger logger = LoggerFactory.getLogger(BankServiceImpl.class);
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankMapper bankMapper;

    @Override
    public List<Bank> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public Page<Bank> findAll(Pageable pageable) {
        return bankRepository.findAll(pageable);
    }

    @Override
    public List<Bank> getAllBankAccounts() {
        return bankRepository.findAllWithAccounts();
    }

    @Override
    public BankDto findBankById(Long id) {
        Bank bank = bankRepository.findById(id).orElse(null);
        if (bank != null) {
            //logger.info("Bank Details: " + bankDto);
            return BankMapper.toBankDto(bank);
        }
       throw new RuntimeException("Bank ID not found!");
    }

    @Override
    public void save(BankDto bankDto, String username) {
        Bank bank;
        if (bankDto.getBankId() != null) {
            // Update Bank Details
            bank = bankRepository.findById(bankDto.getBankId()).orElse(new Bank());
            bank.setName(bankDto.getName());
            bank.setAbbreviation(bankDto.getAbbreviation());
            bank.setBranch(bankDto.getBranch());
            User updatedBy = userRepository.findByUsername(username);
            if (updatedBy != null) {
                bank.setUpdatedBy(updatedBy.getUserId());
            }
            //logger.info("Updated Bank ID No. " + bank.getBankId());
        } else {
            bank = new Bank();
            bank.setName(bankDto.getName());
            bank.setAbbreviation(bankDto.getAbbreviation());
            bank.setBranch(bankDto.getBranch());
            User createdBy = userRepository.findByUsername(username);
            if (createdBy != null) {
                bank.setCreatedBy(createdBy.getUserId());
                bank.setUpdatedBy(createdBy.getUserId());
            }
            //logger.info("New bank created successfully!");
        }
        bankRepository.save(bank);
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepository.deleteById(id);
    }
}
