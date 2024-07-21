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
public class CreditCardDto {

    private Long id;

    @NotNull(message = "Credit card voucher number is required.!")
    @Size(min = 8, message = "CCV Number is between 8 to 50 characters.!")
    private String ccvNumber;

    @NotNull(message = "Payee name is required.!")
    @Size(min = 8, message = "Payee name is between 8 to 50 characters.!")
    private String payeeName;

    @NotNull(message = "Date is required.!")
    private Date date;

    @NotNull(message = "Total amount is required.!")
    private Double totalAmount;

    @NotNull(message = "Amount in words is required.!")
    @Size(min = 8, message = "Amount in words is between 8 to 50 characters.!")
    private String amountInWords;

    @NotNull(message = "Mode of payment is required.!")
    @Size(min = 5, message = "Mode of payment is between 5 to 50 characters.!")
    private String modeOfPayment;

    @NotNull(message = "Bank is required.!")
    @Size(min = 8, message = "Bank is between 5 to 50 characters.!")
    private String bank;
}
