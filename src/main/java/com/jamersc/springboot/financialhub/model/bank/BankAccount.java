package com.jamersc.springboot.financialhub.model.bank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bank_accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "bank")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_id")
    @JsonBackReference
    private Bank bank;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "account_balance")
    private Double accountBalance;

    @OneToMany(mappedBy = "bankAccount", orphanRemoval = true, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH
    })
    private List<Transaction> transactions = new ArrayList<>();

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
