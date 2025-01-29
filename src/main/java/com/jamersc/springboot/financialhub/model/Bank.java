package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "banks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "accounts")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long bankId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "abbreviation")
    private String abbreviation;

    @Column(name = "branch")
    private String branch;

    @OneToMany(mappedBy = "bank", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private List<BankAccount> accounts;

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
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}

