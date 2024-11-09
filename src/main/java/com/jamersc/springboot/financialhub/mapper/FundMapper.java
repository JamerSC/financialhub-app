package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.model.PettyCash;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FundMapper {

    // Convert DTO to Entity
    public static Fund toFundEntity(FundDto fundDto) {
        if (fundDto == null) {
            return null;
        }

        List<PettyCash> pettyCashEntities = fundDto.getPettyCash() != null
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
    }
    // Convert Entity to DTO
    public static FundDto toFundDto(Fund fund) {
        if (fund == null) {
            return null;
        }

        List<PettyCashDto> pettyCashDtos = fund.getPettyCash() != null
                ? fund.getPettyCash().stream().map(PettyCashMapper::toPettyCashDto).collect(Collectors.toList())
                : null;

        return new FundDto(
                fund.getFundId(),
                pettyCashDtos,
                fund.getFundBalance(),
                fund.getCreatedBy(),
                fund.getCreatedAt(),
                fund.getUpdatedBy(),
                fund.getUpdatedAt()
        );
    }
}
