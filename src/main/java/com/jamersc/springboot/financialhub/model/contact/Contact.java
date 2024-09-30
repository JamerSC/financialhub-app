package com.jamersc.springboot.financialhub.model.contact;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"individual", "company", "additionalDetails"})
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date engagementDate;

    @Column(name = "best_channel_to_contact")
    private String bestChannelToContact;

    // Bi-directional relationships
    @OneToOne(mappedBy = "contact", cascade = CascadeType.ALL)
    private ContactIndividual individual;

    @OneToOne(mappedBy = "contact", cascade = CascadeType.ALL)
    private ContactCompany company;

    @OneToOne(mappedBy = "contact", cascade = CascadeType.ALL)
    private ContactAdditionalDetails additionalDetails;

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


