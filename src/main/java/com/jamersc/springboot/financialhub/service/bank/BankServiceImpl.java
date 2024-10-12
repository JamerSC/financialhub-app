package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.dto.BankDto;
import com.jamersc.springboot.financialhub.mapper.BankMapper;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.model.bank.Bank;
import com.jamersc.springboot.financialhub.repository.BankRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import com.jamersc.springboot.financialhub.service.user.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class BankServiceImpl implements BankService{

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
            BankDto bankDto = bankMapper.toBankDto(bank);
            logger.info("Bank Details: " + bankDto);
            return bankDto;
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
                bank.setUpdatedBy(updatedBy.getId());
            }
            logger.info("Updated Bank ID No. " + bank.getBankId());
        } else {
            bank = new Bank();
            bank.setName(bankDto.getName());
            bank.setAbbreviation(bankDto.getAbbreviation());
            bank.setBranch(bankDto.getBranch());
            User createdBy = userRepository.findByUsername(username);
            if (createdBy != null) {
                bank.setCreatedBy(createdBy.getId());
                bank.setUpdatedBy(createdBy.getId());
            }
            logger.info("New bank created successfully!");
        }
        bankRepository.save(bank);
    }

    @Override
    public void deleteBankById(Long id) {
        bankRepository.deleteById(id);
    }
}
