package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.validation.PasswordMatches;
import com.jamersc.springboot.financialhub.validation.RoleIdsNotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@PasswordMatches
public class UserDto {

    private Long userId;

    @NotNull(message = "Full name is required!")
    @Size(min = 1, max = 50, message = "Full name must be between 1 and 50 characters!")
    private String fullName;

    @NotNull(message = "Username is required!")
    @Size(min = 4, max = 30, message = "Username must be between 8 and 30 characters!")
    private String username;

    @NotNull(message = "Password is required!")
    @Size(min = 6, max = 64, message = "Password must be between 6 and 64 characters!")
    private String password;

    @NotNull(message = "Confirm Password is required!")
    private String confirmPassword;

    private Boolean enabled = true;

    private Long createdBy;

    private Date createdAt;

    private Long updatedBy;

    private Date updatedAt;

    private ContactDto contact;
    //private Long contactId; // Instead of ContactDto reference

    @RoleIdsNotEmpty(message = "At least one role must be selected")
    private Set<Long> roleIds;
}
