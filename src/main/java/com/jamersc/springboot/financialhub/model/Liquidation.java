package com.jamersc.springboot.financialhub.model;

import com.jamersc.springboot.financialhub.model.contact.Contact;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade={
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "petty_cash_id")
    private PettyCash pettyCash;

    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Column(name = "particulars")
    private String particulars;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "receipt_no")
    private String receiptNo;

    @Column(name = "remarks")
    private String remarks;

    // Change chargeTo to reference Contact entity
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private Contact chargeTo;

    @Column(name = "billed")
    private boolean billed;

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
