package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = {FundMapper.class, UserMapper.class, ClientAccountMapper.class, LiquidationMapper.class})
public interface PettyCashMapper {

    PettyCashMapper INSTANCE = Mappers.getMapper(PettyCashMapper.class);



    @Mapping(target = "fund", source = "fund")
    //@Mapping(target = "liquidations", expression = "java(mapActivityIds(pettyCashActivity.getLiquidations()))")
    @Mapping(target = "liquidations", source = "liquidations")
    @Mapping(target = "accountIds", expression = "java(mapAccountsToIds(pettyCashActivity.getAccounts()))")
    @Mapping(target = "accountDetails", expression = "java(mapAccountsToDetails(pettyCashActivity.getAccounts()))")
    PettyCashActivityDto toPettyCashActivityDto(PettyCashActivity pettyCashActivity);


    @Mapping(target = "liquidations", ignore = true)
    @Mapping(target = "fund", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    PettyCashActivity toPettyCashActivityEntity(PettyCashActivityDto pettyCashActivityDto);

    default Set<Long> mapAccountsToIds(Set<ClientAccount> accounts) {
        if(accounts == null) {
            return null;
        }
        // stream & map all client accounts id used link used in petty cash
        return accounts.stream()
                .map(ClientAccount::getClientAccountId)
                .collect(Collectors.toSet());
    }

    default Set<String> mapAccountsToDetails(Set<ClientAccount> accounts) {
        if(accounts == null) {
            return null;
        }
        return accounts.stream()
                .map(account -> account.getAccountTitle() + " (" + account.getClientAccountType() + ")")
                .collect(Collectors.toSet());
    }
    default Set<String> mapAccountDetails(Set<ClientAccount> accounts) {
        return accounts != null ? accounts.stream().map(a -> a.getAccountTitle() + " - " + a.getClientAccountType()).collect(Collectors.toSet()) : null;
    }
}
