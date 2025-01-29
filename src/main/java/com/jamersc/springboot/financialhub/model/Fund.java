package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "fund")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fund_id")
    private Long fundId;

    // Petty Cash
    @OneToMany(mappedBy = "fund", fetch = FetchType.LAZY,  orphanRemoval = true, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<PettyCashActivity> pettyCash;

    @Column(name = "fund_balance")
    private Double fundBalance;

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
