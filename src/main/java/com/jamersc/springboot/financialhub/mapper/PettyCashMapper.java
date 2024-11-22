package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PettyCashMapper {

    public static PettyCashActivity toPettyCashEntity(PettyCashActivityDto dto) {
        if (dto == null) {
            return null;
        }

        PettyCashActivity pettyCashActivity = new PettyCashActivity();
        pettyCashActivity.setPcActivityId(dto.getPcActivityId());
        //pettyCash.setFund(FundMapper.toFundEntity(dto.getFund()));
        pettyCashActivity.setPcActivityNo(dto.getPcActivityNo());
        pettyCashActivity.setDate(dto.getDate());
        pettyCashActivity.setActivityDescription(dto.getActivityDescription());
        pettyCashActivity.setActivityCategory(dto.getActivityCategory());
        pettyCashActivity.setSoaCategory(dto.getSoaCategory());
        Set<ClientAccount> accounts = dto.getAccounts().stream()
                .map(clientAccountDto -> {
                    ClientAccount account = new ClientAccount();
                    account.setClientAccountId(clientAccountDto.getClientAccountId());
                    account.setAccountTitle(clientAccountDto.getAccountTitle());
                    account.setClientAccountType(clientAccountDto.getClientAccountType());
                    return account;
                })
                .collect(Collectors.toSet());
        pettyCashActivity.setAccounts(accounts);
        // pettyCash.setLiquidations(dto.getLiquidations());
        pettyCashActivity.setTotalAmount(dto.getTotalAmount());
        pettyCashActivity.setApproved(dto.getApproved());
        pettyCashActivity.setReceivedBy(UserMapper.toUserEntity(dto.getReceivedBy()));
        pettyCashActivity.setApprovedBy(dto.getApprovedBy());
        pettyCashActivity.setCreatedBy(dto.getCreatedBy());
        pettyCashActivity.setCreatedAt(dto.getCreatedAt());
        pettyCashActivity.setUpdatedBy(dto.getUpdatedBy());
        pettyCashActivity.setUpdatedAt(dto.getUpdatedAt());

        return pettyCashActivity;
    }

    public static PettyCashActivityDto toPettyCashDto(PettyCashActivity pettyCashActivity) {
        if (pettyCashActivity == null) {
            return null;
        }

        PettyCashActivityDto dto = new PettyCashActivityDto();
        dto.setPcActivityId(pettyCashActivity.getPcActivityId());
        //dto.setFund(FundMapper.toFundDto(pettyCash.getFund()));
        dto.setPcActivityNo(pettyCashActivity.getPcActivityNo());
        dto.setDate(pettyCashActivity.getDate());
        dto.setActivityDescription(pettyCashActivity.getActivityDescription());
        dto.setActivityCategory(pettyCashActivity.getActivityCategory());
        dto.setSoaCategory(pettyCashActivity.getSoaCategory());
        Set<ClientAccountDto> accounts = pettyCashActivity.getAccounts().stream()
                .map(account -> new ClientAccountDto(
                        account.getClientAccountId(),
                        account.getAccountTitle(),
                        account.getClientAccountType()
                ))
                .collect(Collectors.toSet());
        dto.setAccounts(accounts);
        // pettyCash.setLiquidations(dto.getLiquidations());
        dto.setTotalAmount(pettyCashActivity.getTotalAmount());
        dto.setApproved(pettyCashActivity.getApproved());
        dto.setReceivedBy(UserMapper.toUserDto(pettyCashActivity.getReceivedBy()));
        dto.setApprovedBy(pettyCashActivity.getApprovedBy());
        dto.setCreatedBy(pettyCashActivity.getCreatedBy());
        dto.setCreatedAt(pettyCashActivity.getCreatedAt());
        dto.setUpdatedBy(pettyCashActivity.getUpdatedBy());
        dto.setUpdatedAt(pettyCashActivity.getUpdatedAt());

        return dto;
    }
}
