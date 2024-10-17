package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.dto.BankTransactionDto;
import com.jamersc.springboot.financialhub.model.BankTransaction;
import com.jamersc.springboot.financialhub.model.BankTransactionType;

import java.util.List;

public interface BankTransactionService {

    List<BankTransaction> getAllTransactions();

    BankTransactionDto getTransactionById(Long transactionId);

    List<BankTransaction> findBankAccountById(Long bankAccountId);

    List<BankTransaction> getTransactionType(String transactionType);

    List<BankTransaction> findTransactionsByBankAccountAndType(Long bankAccountId, BankTransactionType transactionType);  // New method

    void save(BankTransactionDto bankTransactionDto, String username);

    void processDeposit(BankTransactionDto deposit, String username);

    void processWithdrawal(BankTransactionDto withdraw, String username);

    void deleteTransactionById(Long transactionId);
}
