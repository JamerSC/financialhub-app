package com.jamersc.springboot.financialhub.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PettyCashActivityDto {

    private Long pcActivityId;

    private FundDto fund;
    //private Long fundId; // ID

    private String pcActivityNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String activityDescription;
    private String activityCategory;
    private String soaCategory;

    //private Set<ClientAccountDto> accounts; // Many to Many Rel.
    private List<LiquidationDto> liquidations; // One to Many
    //private List<Long> activityId; // Liquidation Activity ID

    private Double totalAmount;
    private Boolean approved;
    private UserDto receivedBy; // Many to One (user)
    private Long approvedBy;
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;

    private Set<Long> accountIds; // Custom mapping to store account IDs'
    private Set<String> accountDetails; // Add a new field to store account details (Project title & Type)
}
