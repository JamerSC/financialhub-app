package com.jamersc.springboot.financialhub.model;

import com.jamersc.springboot.financialhub.model.contact.Contact;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "client_accounts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    @OneToOne(mappedBy = "clientAccount",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private CaseAccount caseAccount;

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
