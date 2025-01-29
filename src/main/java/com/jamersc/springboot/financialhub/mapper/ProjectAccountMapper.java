package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ProjectAccountDto;
import com.jamersc.springboot.financialhub.dto.RetainerAccountDto;
import com.jamersc.springboot.financialhub.model.ProjectAccount;
import com.jamersc.springboot.financialhub.model.RetainerAccount;
import org.springframework.stereotype.Component;

@Component
public class ProjectAccountMapper {
    // Convert Entity to DTO
    public static ProjectAccountDto toProjectAccountDto(ProjectAccount projectAccount) {
        if (projectAccount == null) {
            return null;
        }

        ProjectAccountDto projectAccountDto = new ProjectAccountDto();
        projectAccountDto.setProjectId(projectAccount.getProjectId());
        //projectAccountDto.setClientAccount(ClientAccountMapper.toClientAccountDto(projectAccount.getClientAccount())); // Pass the related entity
        projectAccountDto.setProjectType(projectAccount.getProjectType());
        projectAccountDto.setPropertySubType(projectAccount.getPropertySubType());
        projectAccountDto.setBusinessSubType(projectAccount.getBusinessSubType());
        projectAccountDto.setSecSubType(projectAccount.getSecSubType());
        projectAccountDto.setProjectTitle(projectAccount.getProjectTitle());
        projectAccountDto.setTitleNo(projectAccount.getTitleNo());
        projectAccountDto.setTaxDecNo(projectAccount.getTaxDecNo());
        projectAccountDto.setLotNo(projectAccount.getLotNo());
        projectAccountDto.setLotArea(projectAccount.getLotArea());
        projectAccountDto.setLocation(projectAccount.getLocation());
        projectAccountDto.setBir(projectAccount.getBir());
        projectAccountDto.setRd(projectAccount.getRd());
        projectAccountDto.setZonalValue(projectAccount.getZonalValue());
        projectAccountDto.setPurchasePrice(projectAccount.getPurchasePrice());
        projectAccountDto.setRemarks(projectAccount.getRemarks());
        projectAccountDto.setDeceased(projectAccount.getDeceased());
        projectAccountDto.setHeirs(projectAccount.getHeirs());
        projectAccountDto.setAddress(projectAccount.getAddress());
        projectAccountDto.setStatus(projectAccount.getStatus());

        return projectAccountDto;
    }

    // Convert DTO to Entity
    public static ProjectAccount toProjectAccountEntity(ProjectAccountDto projectAccountDto) {
        if (projectAccountDto == null) {
            return null;
        }

        ProjectAccount projectAccount = new ProjectAccount();
        projectAccount.setProjectId(projectAccountDto.getProjectId());
        //projectAccount.setClientAccount(ClientAccountMapper.toClientAccountEntity(projectAccountDto.getClientAccount())); // Pass the related entity
        projectAccount.setProjectType(projectAccountDto.getProjectType());
        projectAccount.setPropertySubType(projectAccountDto.getPropertySubType());
        projectAccount.setBusinessSubType(projectAccountDto.getBusinessSubType());
        projectAccount.setSecSubType(projectAccountDto.getSecSubType());
        projectAccount.setProjectTitle(projectAccountDto.getProjectTitle());
        projectAccount.setTitleNo(projectAccountDto.getTitleNo());
        projectAccount.setTaxDecNo(projectAccountDto.getTaxDecNo());
        projectAccount.setLotNo(projectAccountDto.getLotNo());
        projectAccount.setLotArea(projectAccountDto.getLotArea());
        projectAccount.setLocation(projectAccountDto.getLocation());
        projectAccount.setBir(projectAccountDto.getBir());
        projectAccount.setRd(projectAccountDto.getRd());
        projectAccount.setZonalValue(projectAccountDto.getZonalValue());
        projectAccount.setPurchasePrice(projectAccountDto.getPurchasePrice());
        projectAccount.setRemarks(projectAccountDto.getRemarks());
        projectAccount.setDeceased(projectAccountDto.getDeceased());
        projectAccount.setHeirs(projectAccountDto.getHeirs());
        projectAccount.setAddress(projectAccountDto.getAddress());
        projectAccount.setStatus(projectAccountDto.getStatus());

        return projectAccount;
    }
}
