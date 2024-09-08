package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "bank_deposits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BankDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "deposit_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date depositDate;

    @Column(name = "deposit_amount")
    private Double depositAmount;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_by")
    private int updatedBy;

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
