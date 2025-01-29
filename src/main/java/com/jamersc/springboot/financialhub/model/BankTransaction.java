package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "bank_transactions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"bankAccount"})
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private BankTransactionType transactionType;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Column(name = "transaction_note")
    private String transactionNote;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}

