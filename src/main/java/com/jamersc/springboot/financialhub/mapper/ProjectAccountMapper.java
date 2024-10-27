package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ProjectAccountDto;
import com.jamersc.springboot.financialhub.dto.RetainerAccountDto;
import com.jamersc.springboot.financialhub.model.ProjectAccount;
import com.jamersc.springboot.financialhub.model.RetainerAccount;
import org.springframework.stereotype.Component;

@Component
public class ProjectAccountMapper {

    // Convert DTO to Entity
    public static ProjectAccount toProjectAccountEntity(ProjectAccountDto projectAccountDto) {
        if (projectAccountDto == null) {
            return null;
        }

        ProjectAccount projectAccount = new ProjectAccount();
        projectAccount.setProjectId(projectAccountDto.getProjectId());
        //retainerAccount.setClientAccount(ClientAccountMapper.toClientAccountEntity(retainerAccountDto.getClientAccount())); // Pass the related entity
        projectAccount.setProjectType(projectAccountDto.getProjectType());
        projectAccount.setPropertySubType(projectAccountDto.getPropertySubType());
        projectAccount.setSecSubType(projectAccountDto.getSecSubType());
        projectAccount.setProjectTitle(projectAccountDto.getProjectTitle());
        projectAccount.setStatus(projectAccountDto.getStatus());

        return projectAccount;
    }

    // Convert Entity to DTO
    public static ProjectAccountDto toProjectAccountDto(ProjectAccount projectAccount) {
        if (projectAccount == null) {
            return null;
        }

        ProjectAccountDto projectAccountDto = new ProjectAccountDto();
        projectAccountDto.setProjectId(projectAccount.getProjectId());
        //retainerAccount.setClientAccount(ClientAccountMapper.toClientAccountEntity(retainerAccountDto.getClientAccount())); // Pass the related entity
        projectAccountDto.setProjectType(projectAccount.getProjectType());
        projectAccountDto.setPropertySubType(projectAccount.getPropertySubType());
        projectAccountDto.setSecSubType(projectAccount.getSecSubType());
        projectAccountDto.setProjectTitle(projectAccount.getProjectTitle());
        projectAccountDto.setStatus(projectAccount.getStatus());

        return projectAccountDto;
    }
}
