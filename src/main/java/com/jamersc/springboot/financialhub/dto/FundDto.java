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

    private Long id;
    private List<PettyCashDto> pettyCash;
    private Double fundBalance;
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
}
