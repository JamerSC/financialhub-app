package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.Fund;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PettyCashDto {

    private Long id;

    @NotNull(message = "Petty cash voucher number is required!")
    @Size(min = 8, message = "PCV Number is between 8 to 50 characters!")
    private String pcvNumber;

    @NotNull(message = "Receiver name is required!")
    @Size(min = 8, message = "Receiver name is between 8 - 50 characters!")
    private String receivedBy;

    @NotNull(message = "Date is required!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @NotNull(message = "Particular is required!")
    @Size(min = 3, message = "Particulars must between 3 to 50 characters!")
    private String particulars;

    @NotNull(message = "Total amount is required!")
    private Double totalAmount;

    @NotNull(message = "Approver name is required!")
    @Size(min = 8, message = "Approver name is between 8 - 50 characters!")
    private String approvedBy;

    private Fund fund;
}
