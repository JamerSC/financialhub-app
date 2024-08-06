package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "petty_cash_vouchers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PettyCash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "pcv_number")
    private String pcvNumber;

    @Column(name = "received_by")
    private String receivedBy;

    @Column(name = "date")
    private Date date;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "approved_by")
    private String approvedBy;

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
