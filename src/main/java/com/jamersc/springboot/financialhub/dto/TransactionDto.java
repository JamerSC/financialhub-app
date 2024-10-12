package com.jamersc.springboot.financialhub.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.jamersc.springboot.financialhub.model.bank.BankAccount;
import com.jamersc.springboot.financialhub.model.bank.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionDto {

    private Long id;  // Unique identifier for the transaction
    private Long bankAccountId;  // Reference to the BankAccount
    private String transactionType;  // Credit, Debit, etc.
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date transactionDate;  // Date of the transaction
    private Double transactionAmount;  // Amount involved in the transaction
    private String transactionNote;  // Note or description of the transaction
    private Long createdBy;  // ID of the user who created the transaction
    private Date createdAt;  // Timestamp of when the transaction was created
    private Long updatedBy;  // ID of the user who updated the transaction
    private Date updatedAt;  // Timestamp of when the transaction was updated
}

