package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.CaseAccountDto;
import com.jamersc.springboot.financialhub.model.CaseAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//@Component
@Mapper(uses = {ClientAccountMapper.class})
public interface CaseAccountMapper {

    CaseAccountMapper INSTANCE = Mappers.getMapper(CaseAccountMapper.class);

    // Convert Entity to DTO
    @Mapping(source = "clientAccount", target = "clientAccount") // Maps the related ClientAccount entity
    @Mapping(source = "caseType", target = "caseType")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    CaseAccountDto toCaseAccountDto(CaseAccount caseAccount);

    // Convert DTO to Entity
    @Mapping(source = "clientAccount", target = "clientAccount") // Maps the related ClientAccount DTO
    @Mapping(source = "caseType", target = "caseType")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    CaseAccount toCaseAccountEntity(CaseAccountDto caseAccountDto);


    // Convert Entity to DTO
   /* public static CaseAccountDto toCaseAccountDto(CaseAccount caseAccount) {
        if (caseAccount == null) {
            return null;
        }

        CaseAccountDto caseAccountDto = new CaseAccountDto();
        caseAccountDto.setCaseId(caseAccount.getCaseId());
        //caseAccountDto.setClientAccount(ClientAccountMapper.toClientAccountDto(caseAccount.getClientAccount())); // Pass the related entity
        caseAccountDto.setCaseType(caseAccount.getCaseType()); // Set the CaseType enum based on the DTO
        caseAccountDto.setCaseTitle(caseAccount.getCaseTitle());
        caseAccountDto.setDocketNo(caseAccount.getDocketNo());
        caseAccountDto.setNature(caseAccount.getNature());
        caseAccountDto.setCourt(caseAccount.getCourt());
        caseAccountDto.setBranch(caseAccount.getBranch());
        caseAccountDto.setJudge(caseAccount.getJudge());
        caseAccountDto.setCourtEmail(caseAccount.getCourtEmail());
        caseAccountDto.setProsecutor(caseAccount.getProsecutor());
        caseAccountDto.setProsecutorOffice(caseAccount.getProsecutorOffice());
        caseAccountDto.setProsecutorEmail(caseAccount.getProsecutorEmail());
        caseAccountDto.setOpposingParty(caseAccount.getOpposingParty());
        caseAccountDto.setOpposingCounsel(caseAccount.getOpposingCounsel());
        caseAccountDto.setCounselEmail(caseAccount.getCounselEmail());
        caseAccountDto.setStatus(caseAccount.getStatus()); // Set the Status enum based on the DTO
        caseAccountDto.setStage(caseAccount.getStage());
        caseAccountDto.setStartDate(caseAccount.getStartDate());
        caseAccountDto.setEndDate(caseAccount.getEndDate());

        return caseAccountDto;
    }
    // Convert DTO to Entity
    public static CaseAccount toCaseAccountEntity(CaseAccountDto caseAccountDto) {
        if (caseAccountDto == null) {
            return null;
        }

        CaseAccount caseAccount = new CaseAccount();
        caseAccount.setCaseId(caseAccountDto.getCaseId());
        //caseAccount.setClientAccount(ClientAccountMapper.toClientAccountEntity(caseAccountDto.getClientAccount())); // Pass the related entity
        caseAccount.setCaseType(caseAccountDto.getCaseType()); // Set the CaseType enum based on the DTO
        caseAccount.setCaseTitle(caseAccountDto.getCaseTitle());
        caseAccount.setDocketNo(caseAccountDto.getDocketNo());
        caseAccount.setNature(caseAccountDto.getNature());
        caseAccount.setCourt(caseAccountDto.getCourt());
        caseAccount.setBranch(caseAccountDto.getBranch());
        caseAccount.setJudge(caseAccountDto.getJudge());
        caseAccount.setCourtEmail(caseAccountDto.getCourtEmail());
        caseAccount.setProsecutor(caseAccountDto.getProsecutor());
        caseAccount.setProsecutorOffice(caseAccountDto.getProsecutorOffice());
        caseAccount.setProsecutorEmail(caseAccountDto.getProsecutorEmail());
        caseAccount.setOpposingParty(caseAccountDto.getOpposingParty());
        caseAccount.setOpposingCounsel(caseAccountDto.getOpposingCounsel());
        caseAccount.setCounselEmail(caseAccountDto.getCounselEmail());
        caseAccount.setStatus(caseAccountDto.getStatus()); // Set the Status enum based on the DTO
        caseAccount.setStage(caseAccountDto.getStage());
        caseAccount.setStartDate(caseAccountDto.getStartDate());
        caseAccount.setEndDate(caseAccountDto.getEndDate());

        return caseAccount;
    }*/
}
