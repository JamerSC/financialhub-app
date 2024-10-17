package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.BankTransactionType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankTransactionDto {

    private Long id;
    private BankAccountDto bankAccount;  // @ManyToOne - Nested BankAccountDto
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date transactionDate;
    private BankTransactionType transactionType;
    private Double transactionAmount;
    private String transactionNote;
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
}

