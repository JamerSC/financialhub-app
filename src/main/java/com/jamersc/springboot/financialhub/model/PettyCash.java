package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "petty_cash_vouchers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"fund", "accounts", "liquidations"})
public class PettyCash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petty_cash_id")
    private Long pettyCashId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fund_id")
    private Fund fund;

    @Column(name = "pc_voucher_no")
    private String voucherNo;

    @Column(name = "date")
    private Date date;

    @Column(name = "activity_description",columnDefinition = "TEXT")
    private String activityDescription;

    @Column(name = "activity_category")
    private String activityCategory;

    @Column(name = "soa_category")
    private String soaCategory;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "petty_cash_client_accounts",
            joinColumns = @JoinColumn(name = "petty_cash_id"),
            inverseJoinColumns = @JoinColumn(name = "client_account_id")
    )
    private Set<ClientAccount> accounts;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "received_by", updatable = false)
    private Long receivedBy;

    @Column(name = "approved_by")
    private Long approvedBy;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pettyCash",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = true)
    private List<Liquidation> liquidations;

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
