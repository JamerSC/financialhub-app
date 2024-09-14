package com.jamersc.springboot.financialhub.service.bank;

import com.jamersc.springboot.financialhub.model.Transaction;
import com.jamersc.springboot.financialhub.model.TransactionType;
import com.jamersc.springboot.financialhub.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(()-> new RuntimeException("Transaction ID not found."));
    }

    @Override
    public List<Transaction> findBankAccountById(Long bankAccountId) {
        return transactionRepository.findByBankAccount_Id(bankAccountId);
    }

    @Override
    public List<Transaction> getTransactionType(String transactionType) {
        return transactionRepository.findByTransactionType(transactionType);
    }

    @Override
    public List<Transaction> findTransactionsByBankAccountAndType(Long bankAccountId, TransactionType transactionType) {
        return transactionRepository.findByBankAccount_IdAndTransactionType(bankAccountId, transactionType);
    }

    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }


}
