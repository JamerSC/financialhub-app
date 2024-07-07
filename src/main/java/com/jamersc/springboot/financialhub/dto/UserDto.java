package com.jamersc.springboot.financialhub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    @NotNull(message = "Firstname is required!")
    @Size(min = 1, max = 50, message = "Firstname must be between 1 and 50 characters!")
    private String firstName;
    @NotNull(message = "Lastname is required!")
    @Size(min = 1, max = 50, message = "Lastname must be between 1 and 50 characters!")
    private String lastName;
    @NotNull(message = "Middle name is required!")
    @Size(min = 1, max = 50, message = "Middle name must be between 1 and 50 characters!")
    private String middleName;
    @NotNull(message = "Email is required!")
    @Email(message = "Invalid email format!")
    private String email;
    @NotNull(message = "Username is required!")
    @Size(min = 8, max = 30, message = "Username must be between 8 and 30 characters!")
    private String username;
    @NotNull(message = "Password is required!")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters!")
    private String password;
    @NotNull(message = "Enabled is required!")
    private boolean enabled;
}
