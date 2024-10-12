package com.jamersc.springboot.financialhub.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jamersc.springboot.financialhub.model.bank.Bank;
import com.jamersc.springboot.financialhub.model.bank.Transaction;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankAccountDto {

    private Long bankAccountId;
    private BankDto bank;  // Nested BankDto
    private String accountHolderName;
    private String accountNumber;
    private Double accountBalance;
    private List<TransactionDto> transactions;  // List of nested TransactionDto
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
}