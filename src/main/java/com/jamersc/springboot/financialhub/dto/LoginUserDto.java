package com.jamersc.springboot.financialhub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LoginUserDto {
    @NotNull(message = "Username is Required!")
    @Size(min = 8, max = 30, message = "Username must be between 8 and 30 characters!")
    private String username;
    @NotNull(message = "Password is Required!")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters!")
    private String password;
}
