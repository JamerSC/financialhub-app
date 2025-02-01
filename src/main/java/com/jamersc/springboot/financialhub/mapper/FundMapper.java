package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {PettyCashMapper.class})
//@Component
public interface FundMapper {
    //class FundMapper {

    FundMapper INSTANCE = Mappers.getMapper(FundMapper.class);

    @Mapping(source = "pettyCash", target = "pettyCash")
    FundDto toFundDto(Fund fund);

    @Mapping(source = "pettyCash", target = "pettyCash")
    Fund toFundEntity(FundDto fundDto);

    default List<PettyCashActivityDto> mapPettyCashToDto(List<PettyCashActivity> pettyCash) {
        return pettyCash != null ? pettyCash.stream().map(PettyCashMapper.INSTANCE::toPettyCashActivityDto).collect(Collectors.toList()) : null;
    }

    default List<PettyCashActivity> mapDtoToPettyCash(List<PettyCashActivityDto> pettyCashDtos) {
        return pettyCashDtos != null ? pettyCashDtos.stream().map(PettyCashMapper.INSTANCE::toPettyCashActivityEntity).collect(Collectors.toList()) : null;
    }


    // Convert Entity to DTO
/*    public static FundDto toFundDto(Fund fund) {
        if (fund == null) {
            return null;
        }

        List<PettyCashActivityDto> pettyCashActivityDtos = fund.getPettyCash() != null
                ? fund.getPettyCash().stream().map(PettyCashMapper::toPettyCashDto).collect(Collectors.toList())
                : null;

        return new FundDto(
                fund.getFundId(),
                pettyCashActivityDtos,
                fund.getFundBalance(),
                fund.getCreatedBy(),
                fund.getCreatedAt(),
                fund.getUpdatedBy(),
                fund.getUpdatedAt()
        );
    }

    // Convert DTO to Entity
    public static Fund toFundEntity(FundDto fundDto) {
        if (fundDto == null) {
            return null;
        }

        List<PettyCashActivity> pettyCashEntities = fundDto.getPettyCash() != null
                ? fundDto.getPettyCash().stream().map(PettyCashMapper::toPettyCashEntity).collect(Collectors.toList())
                : null;

        Fund fund = new Fund();
        fund.setFundId(fundDto.getFundId());
        fund.setPettyCash(pettyCashEntities);
        fund.setFundBalance(fundDto.getFundBalance());
        fund.setCreatedBy(fundDto.getCreatedBy());
        fund.setCreatedAt(fundDto.getCreatedAt());
        fund.setUpdatedBy(fundDto.getUpdatedBy());
        fund.setUpdatedAt(fundDto.getUpdatedAt());

        return fund;
    }*/
}
