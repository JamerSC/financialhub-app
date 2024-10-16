package com.jamersc.springboot.financialhub.model.contact;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contact_individual")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "contact")
public class ContactIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "individual_id")
    private Long individualId;

    @OneToOne
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    @Column(name = "title")
    private String title;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "suffix")
    private String suffix;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "address",columnDefinition = "TEXT")
    private String address;
}
