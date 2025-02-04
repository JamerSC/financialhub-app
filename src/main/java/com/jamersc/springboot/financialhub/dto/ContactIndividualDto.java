package com.jamersc.springboot.financialhub.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactIndividualDto {

    private Long individualId;
    //private ContactDto contact; // @OneToOne contact id
    private Long contactId; // Instead of ContactDto reference

    private String title;
    private String lastName;
    private String firstName;
    private String middleName;
    private String suffix;
    private String mobileNumber;
    private String emailAddress;
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
