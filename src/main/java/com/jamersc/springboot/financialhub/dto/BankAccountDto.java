package com.jamersc.springboot.financialhub.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankAccountDto {

    private Long bankAccountId;
    private Long bankId;
    private String accountHolderName;
    private String accountNumber;
    private Double accountBalance;
    private List<BankTransactionDto> transactions;  // @OneToMany List of nested TransactionDto
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
}