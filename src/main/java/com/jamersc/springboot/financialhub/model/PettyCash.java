package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "petty_cash_vouchers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "liquidations")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pettyCash",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true)
    private List<Liquidation> liquidations = new ArrayList<>();

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
