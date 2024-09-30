package com.jamersc.springboot.financialhub.model.contact;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_additional_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContactAdditionalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long detailId;

    @OneToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @Column(name = "designation_for")
    private String designationFor;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_no")
    private String accountNo;
}
