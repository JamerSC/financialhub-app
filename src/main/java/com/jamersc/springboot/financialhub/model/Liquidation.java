package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "petty_cash_activity_entries")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"activity", "chargeTo"})
public class Liquidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    // Reference to PettyCashActivity Entity
    @ManyToOne(fetch = FetchType.LAZY, cascade={
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "pc_activity_id")
    private PettyCashActivity activity;

    @Column(name = "date")
    private Date date;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "remarks")
    private String remarks;

    // Reference to ClientAccount Entity
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_account_id")
    private ClientAccount chargeTo;

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
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
