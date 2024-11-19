package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.PettyCash;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PettyCashMapper {

    public static PettyCash toPettyCashEntity(PettyCashDto dto) {
        if (dto == null) {
            return null;
        }

        PettyCash pettyCash = new PettyCash();
        pettyCash.setPettyCashId(dto.getPettyCashId());
        //pettyCash.setFund(FundMapper.toFundEntity(dto.getFund()));
        pettyCash.setVoucherNo(dto.getVoucherNo());
        pettyCash.setDate(dto.getDate());
        pettyCash.setActivityDescription(dto.getActivityDescription());
        pettyCash.setActivityCategory(dto.getActivityCategory());
        pettyCash.setSoaCategory(dto.getSoaCategory());
        Set<ClientAccount> accounts = dto.getAccounts().stream()
                .map(clientAccountDto -> {
                    ClientAccount account = new ClientAccount();
                    account.setClientAccountId(clientAccountDto.getClientAccountId());
                    account.setAccountTitle(clientAccountDto.getAccountTitle());
                    account.setClientAccountType(clientAccountDto.getClientAccountType());
                    return account;
                })
                .collect(Collectors.toSet());
        pettyCash.setAccounts(accounts);
        // pettyCash.setLiquidations(dto.getLiquidations());
        pettyCash.setTotalAmount(dto.getTotalAmount());
        pettyCash.setApproved(dto.getApproved());
        pettyCash.setReceivedBy(UserMapper.toUserEntity(dto.getReceivedBy()));
        pettyCash.setApprovedBy(dto.getApprovedBy());
        pettyCash.setCreatedBy(dto.getCreatedBy());
        pettyCash.setCreatedAt(dto.getCreatedAt());
        pettyCash.setUpdatedBy(dto.getUpdatedBy());
        pettyCash.setUpdatedAt(dto.getUpdatedAt());

        return pettyCash;
    }

    public static PettyCashDto toPettyCashDto(PettyCash pettyCash) {
        if (pettyCash == null) {
            return null;
        }

        PettyCashDto dto = new PettyCashDto();
        dto.setPettyCashId(pettyCash.getPettyCashId());
        //dto.setFund(FundMapper.toFundDto(pettyCash.getFund()));
        dto.setVoucherNo(pettyCash.getVoucherNo());
        dto.setDate(pettyCash.getDate());
        dto.setActivityDescription(pettyCash.getActivityDescription());
        dto.setActivityCategory(pettyCash.getActivityCategory());
        dto.setSoaCategory(pettyCash.getSoaCategory());
        Set<ClientAccountDto> accounts = pettyCash.getAccounts().stream()
                .map(account -> new ClientAccountDto(
                        account.getClientAccountId(),
                        account.getAccountTitle(),
                        account.getClientAccountType()
                ))
                .collect(Collectors.toSet());
        dto.setAccounts(accounts);
        // pettyCash.setLiquidations(dto.getLiquidations());
        dto.setTotalAmount(pettyCash.getTotalAmount());
        dto.setApproved(pettyCash.getApproved());
        dto.setReceivedBy(UserMapper.toUserDto(pettyCash.getReceivedBy()));
        dto.setApprovedBy(pettyCash.getApprovedBy());
        dto.setCreatedBy(pettyCash.getCreatedBy());
        dto.setCreatedAt(pettyCash.getCreatedAt());
        dto.setUpdatedBy(pettyCash.getUpdatedBy());
        dto.setUpdatedAt(pettyCash.getUpdatedAt());

        return dto;
    }
}
