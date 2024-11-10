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

    public static PettyCash toPettyCashEntity(PettyCashDto pettyCashDto) {
        if (pettyCashDto == null) {
            return null;
        }

        PettyCash pettyCash = new PettyCash();
        pettyCash.setPettyCashId(pettyCashDto.getPettyCashId());
        //pettyCash.setFund(FundMapper.toFundEntity(pettyCashDto.getFund()));
        pettyCash.setVoucherNo(pettyCashDto.getVoucherNo());
        pettyCash.setDate(pettyCashDto.getDate());
        pettyCash.setActivityDescription(pettyCashDto.getActivityDescription());
        pettyCash.setActivityCategory(pettyCashDto.getActivityCategory());
        pettyCash.setSoaCategory(pettyCashDto.getSoaCategory());
        Set<ClientAccount> accounts = pettyCashDto.getAccounts().stream()
                .map(clientAccountDto -> {
                    ClientAccount account = new ClientAccount();
                    account.setClientAccountId(clientAccountDto.getClientAccountId());
                    account.setAccountTitle(clientAccountDto.getAccountTitle());
                    account.setClientAccountType(clientAccountDto.getClientAccountType());
                    return account;
                })
                .collect(Collectors.toSet());
        pettyCash.setAccounts(accounts);
        // pettyCash.setLiquidations(pettyCashDto.getLiquidations());
        pettyCash.setTotalAmount(pettyCashDto.getTotalAmount());
        pettyCash.setApproved(pettyCashDto.getApproved());
        pettyCash.setReceivedBy(pettyCashDto.getReceivedBy());
        pettyCash.setApprovedBy(pettyCashDto.getApprovedBy());
        pettyCash.setCreatedBy(pettyCashDto.getCreatedBy());
        pettyCash.setCreatedAt(pettyCashDto.getCreatedAt());
        pettyCash.setUpdatedBy(pettyCashDto.getUpdatedBy());
        pettyCash.setUpdatedAt(pettyCashDto.getUpdatedAt());

        return pettyCash;
    }

    public static PettyCashDto toPettyCashDto(PettyCash pettyCash) {
        if (pettyCash == null) {
            return null;
        }

        PettyCashDto pettyCashDto = new PettyCashDto();
        pettyCashDto.setPettyCashId(pettyCash.getPettyCashId());
        //pettyCashDto.setFund(FundMapper.toFundDto(pettyCash.getFund()));
        pettyCashDto.setVoucherNo(pettyCash.getVoucherNo());
        pettyCashDto.setDate(pettyCash.getDate());
        pettyCashDto.setActivityDescription(pettyCash.getActivityDescription());
        pettyCashDto.setActivityCategory(pettyCash.getActivityCategory());
        pettyCashDto.setSoaCategory(pettyCash.getSoaCategory());
        Set<ClientAccountDto> accounts = pettyCash.getAccounts().stream()
                .map(account -> new ClientAccountDto(
                        account.getClientAccountId(),
                        account.getAccountTitle(),
                        account.getClientAccountType()
                ))
                .collect(Collectors.toSet());
        pettyCashDto.setAccounts(accounts);
        // pettyCash.setLiquidations(pettyCashDto.getLiquidations());
        pettyCashDto.setTotalAmount(pettyCash.getTotalAmount());
        pettyCashDto.setApproved(pettyCash.getApproved());
        pettyCashDto.setReceivedBy(pettyCash.getReceivedBy());
        pettyCashDto.setApprovedBy(pettyCash.getApprovedBy());
        pettyCashDto.setCreatedBy(pettyCash.getCreatedBy());
        pettyCashDto.setCreatedAt(pettyCash.getCreatedAt());
        pettyCashDto.setUpdatedBy(pettyCash.getUpdatedBy());
        pettyCashDto.setUpdatedAt(pettyCash.getUpdatedAt());

        return pettyCashDto;
    }
}
