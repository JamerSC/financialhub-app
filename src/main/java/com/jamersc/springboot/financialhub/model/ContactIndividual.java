package com.jamersc.springboot.financialhub.model;

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

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();

        // Append title if present
        if (title != null && !title.isEmpty()) {
            fullName.append(title).append(" ");
        }

        // Append first name if present
        if (firstName != null && !firstName.isEmpty()) {
            fullName.append(firstName).append(" ");
        }

        // Append middle name if present
        if (middleName != null && !middleName.isEmpty()) {
            fullName.append(middleName).append(" ");
        }

        // Append last name if present
        if (lastName != null && !lastName.isEmpty()) {
            fullName.append(lastName);
        }

        // Append suffix if present, adding a comma before the suffix
        if (suffix != null && !suffix.isEmpty()) {
            fullName.append(", ").append(suffix);
        }

        // Trim any extra spaces and return the full name
        return fullName.toString().trim();
    }

}
