package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = {FundMapper.class, UserMapper.class, ClientAccountMapper.class, LiquidationMapper.class})
public interface PettyCashMapper {

    PettyCashMapper INSTANCE = Mappers.getMapper(PettyCashMapper.class);

    @Mapping(source = "fund", target = "fund")
    @Mapping(source = "receivedBy", target = "receivedBy")
    @Mapping(source = "accounts", target = "accounts")
    @Mapping(source = "liquidations", target = "liquidations")
    @Mapping(target = "accountIds", expression = "java(mapAccountIds(pettyCashActivity.getAccounts()))")
    @Mapping(target = "accountDetails", expression = "java(mapAccountDetails(pettyCashActivity.getAccounts()))")
    PettyCashActivityDto toPettyCashActivityDto(PettyCashActivity pettyCashActivity);

    @Mapping(source = "fund", target = "fund")
    @Mapping(source = "receivedBy", target = "receivedBy")
    @Mapping(source = "accounts", target = "accounts")
    @Mapping(source = "liquidations", target = "liquidations")
    PettyCashActivity toPettyCashActivityEntity(PettyCashActivityDto pettyCashActivityDto);

    default Set<Long> mapAccountIds(Set<ClientAccount> accounts) {
        return accounts != null ? accounts.stream().map(ClientAccount::getClientAccountId).collect(Collectors.toSet()) : null;
    }

    default Set<String> mapAccountDetails(Set<ClientAccount> accounts) {
        return accounts != null ? accounts.stream().map(a -> a.getAccountTitle() + " - " + a.getClientAccountType()).collect(Collectors.toSet()) : null;
    }
}
