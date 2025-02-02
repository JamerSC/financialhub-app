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
//@Component
public interface FundMapper {
    //class FundMapper {

    FundMapper INSTANCE = Mappers.getMapper(FundMapper.class);

    @Mapping(target = "pettyCash", source = "pettyCash")
    FundDto toFundDto(Fund fund);

    @Mapping(target = "pettyCash", source = "pettyCash")
    Fund toFundEntity(FundDto fundDto);

    default List<PettyCashActivityDto> mapPettyCashToDto(List<PettyCashActivity> pettyCash) {
        return pettyCash != null ? pettyCash.stream().map(PettyCashMapper.INSTANCE::toPettyCashActivityDto).collect(Collectors.toList()) : null;
    }

    default List<PettyCashActivity> mapDtoToPettyCash(List<PettyCashActivityDto> pettyCashDtos) {
        return pettyCashDtos != null ? pettyCashDtos.stream().map(PettyCashMapper.INSTANCE::toPettyCashActivityEntity).collect(Collectors.toList()) : null;
    }
}
