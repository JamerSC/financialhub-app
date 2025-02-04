package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RetainerAccountDto {

    private Long retainerId;
    //private ClientAccountDto clientAccount;
    private Long clientAccountId; // ID
    private String retainerTitle;
    private Status status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
