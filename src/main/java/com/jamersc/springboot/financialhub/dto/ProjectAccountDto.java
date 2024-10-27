package com.jamersc.springboot.financialhub.dto;

import com.jamersc.springboot.financialhub.model.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectAccountDto {

    private Long projectId;
    private ClientAccount clientAccount;
    private ProjectType projectType;
    private PropertySubType propertySubType;
    private SecSubType secSubType;
    private String projectTitle;
    private Status status;
}
