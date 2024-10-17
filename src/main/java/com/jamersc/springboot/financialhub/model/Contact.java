package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"individual", "company", "additionalDetails", "clientAccounts"})
public class Contact {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long contactId;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_type")
    private ContactType contactType;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_type")
    private ContactCategoryType contactCategoryType;

    @Column(name = "engagement_date")
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date engagementDate;

    @Column(name = "best_channel_to_contact")
    private String bestChannelToContact;

    // Bidirectional relationships
    @OneToOne(mappedBy = "contact",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private ContactIndividual individual;

    @OneToOne(mappedBy = "contact",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private ContactCompany company;

    @OneToOne(mappedBy = "contact",
            cascade={CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private ContactDetails additionalDetails;

    // Bidirectional relationships
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    //@JsonIgnore
    private List<ClientAccount> clientAccounts = new ArrayList<>();

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


