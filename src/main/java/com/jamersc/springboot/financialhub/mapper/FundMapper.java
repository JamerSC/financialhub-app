package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {PettyCashMapper.class})
public interface FundMapper {

    FundMapper INSTANCE = Mappers.getMapper(FundMapper.class);

    @Mapping(target = "pcActivityId", expression = "java(mapPettyCashToIds(fund.getPettyCash()))")
    //@Mapping(target = "pcActivityId", source = "pettyCash")
    FundDto toFundDto(Fund fund);

    @Mapping(target = "pettyCash", expression = "java(mapIdsToPettyCash(fundDto.getPcActivityId()))")
    //@Mapping(target = "pettyCash", ignore = true) // source = "pcActivityId"
    Fund toFundEntity(FundDto fundDto);

    default List<Long> mapPettyCashToIds(List<PettyCashActivity> pettyCash) {
        return pettyCash != null ? pettyCash.stream().map(PettyCashActivity::getPcActivityId).collect(Collectors.toList()) : null;
    }

    default List<PettyCashActivity> mapIdsToPettyCash(List<Long> ids) {
        // You need to fetch PettyCashActivity from the database using IDs
        return ids != null ? ids.stream().map(id -> {
            PettyCashActivity pca = new PettyCashActivity();
            pca.setPcActivityId(id);
            return pca;
        }).collect(Collectors.toList()) : null;
    }
}
