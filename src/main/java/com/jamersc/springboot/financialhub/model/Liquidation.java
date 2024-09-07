package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "petty_cash_liquidation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "pettyCash")
public class Liquidation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "pcvoucher_id")
    private PettyCash pettyCash;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "charge_to")
    private String chargeTo;

    @Column(name = "billed")
    private boolean billed;

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
        Date now = new Date();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
