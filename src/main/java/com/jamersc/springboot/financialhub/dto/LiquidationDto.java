package com.jamersc.springboot.financialhub.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LiquidationDto {

    private Long activityId;
    private PettyCashActivityDto pettyCash;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String particulars;
    private Double cost;
    private String receiptNo;
    private String remarks;
    private ClientAccountDto chargeTo;
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
}
