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



    @Mapping(target = "fundId", source = "fund.fundId")
    @Mapping(target = "activityId", expression = "java(mapActivityIds(pettyCashActivity.getLiquidations()))")
    @Mapping(target = "accountIds", expression = "java(mapAccountIds(pettyCashActivity.getAccounts()))")
    @Mapping(target = "accountDetails", expression = "java(mapAccountDetails(pettyCashActivity.getAccounts()))")
    PettyCashActivityDto toPettyCashActivityDto(PettyCashActivity pettyCashActivity);


    @Mapping(target = "liquidations", ignore = true)
    @Mapping(target = "fund", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    PettyCashActivity toPettyCashActivityEntity(PettyCashActivityDto pettyCashActivityDto);

    default List<Long> mapActivityIds(List<Liquidation> liquidations) {
        return liquidations != null ? liquidations.stream()
                .map(Liquidation::getActivityId)
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    default Set<Long> mapAccountIds(Set<ClientAccount> accounts) {
        return accounts != null ? accounts.stream().map(ClientAccount::getClientAccountId).collect(Collectors.toSet()) : null;
    }

    default Set<String> mapAccountDetails(Set<ClientAccount> accounts) {
        return accounts != null ? accounts.stream().map(a -> a.getAccountTitle() + " - " + a.getClientAccountType()).collect(Collectors.toSet()) : null;
    }
}
