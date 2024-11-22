package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "client_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"client", "caseAccount", "projectAccount", "retainerAccount", "pettyCash"})
public class ClientAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_account_id")
    private Long clientAccountId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private Contact client;

    @Column(name = "account_title")
    private String accountTitle;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private ClientAccountType clientAccountType;

    @OneToOne(mappedBy = "clientAccount", fetch = FetchType.EAGER, orphanRemoval = true, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private CaseAccount caseAccount;

    @OneToOne(mappedBy = "clientAccount", fetch = FetchType.EAGER, orphanRemoval = true, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private ProjectAccount projectAccount;

    @OneToOne(mappedBy = "clientAccount", fetch = FetchType.EAGER, orphanRemoval = true, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private RetainerAccount retainerAccount;

    @ManyToMany(mappedBy = "accounts", fetch = FetchType.LAZY)
    private Set<PettyCashActivity> pettyCashActivity;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    //@Column(name = "updated_at")
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
