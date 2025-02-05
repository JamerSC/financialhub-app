package com.jamersc.springboot.financialhub.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FundDto {

    private Long fundId;
    // private List<PettyCashActivityDto> pettyCash;
    private List<Long> pcActivityId; // ID
    private Double fundBalance;
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
}
