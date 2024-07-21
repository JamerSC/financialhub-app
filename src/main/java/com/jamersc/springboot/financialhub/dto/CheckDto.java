package com.jamersc.springboot.financialhub.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CheckDto {

    private Long id;

    @NotNull(message = "Check voucher number is required.!")
    @Size(min = 8, max = 50, message = "Check voucher must be between 8 and 50 characters.!")
    private String cvNumber;

    @NotNull(message = "Payee name is required.!")
    @Size(min = 8, max = 50, message = "Payee must be between 8 and 50 characters.!")
    private String payeeName;

    @NotNull(message = "Date is required.!")
    private Date date;

    @NotNull(message = "Total amount is required.!")
    private Double totalAmount;

    @NotNull(message = "Amount in words is required.")
    @Size(min = 1, message = "At least 3 to 50 characters.!")
    private String amountInWords;

    @NotNull(message = "Bank is required.!")
    @Size(min = 1, message = "At least 3 to 50 characters.!")
    private String bank;

    @NotNull(message = "Check number is required.!")
    @Size(min = 8, message = "At least 8 to 50 characters.!")
    private String checkNumber;
}
