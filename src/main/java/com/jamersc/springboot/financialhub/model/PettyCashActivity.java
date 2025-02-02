package com.jamersc.springboot.financialhub.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "petty_cash_activities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"fund", "accounts", "liquidations", "receivedBy"})
public class PettyCashActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_activity_id")
    private Long pcActivityId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fund_id")
    private Fund fund;

    @Column(name = "pc_activity_no")
    private String pcActivityNo;

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
            joinColumns = @JoinColumn(name = "pc_activity_id"),
            inverseJoinColumns = @JoinColumn(name = "client_account_id")
    )
    private Set<ClientAccount> accounts;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "approved")
    private Boolean approved;

    @ManyToOne
    @JoinColumn(name = "received_by")
    private User receivedBy;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "activity",
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
