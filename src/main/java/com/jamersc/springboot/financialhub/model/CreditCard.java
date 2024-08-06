package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "credit_card_vouchers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ccv_number")
    private String ccvNumber;

    @Column(name = "payee_name")
    private String payeeName;

    @Column(name = "date")
    private Date date;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "amount_in_words")
    private String amountInWords;

    @Column(name = "mode_of_payment")
    private String modeOfPayment;

    @Column(name = "bank")
    private String bank;

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
