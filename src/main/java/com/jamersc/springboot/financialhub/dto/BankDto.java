package com.jamersc.springboot.financialhub.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankDto {

    private Long bankId;
    private String name;
    private String abbreviation;
    private String branch;
    private Long createdBy;
    private Date createdAt;
    private Long updatedBy;
    private Date updatedAt;
}
