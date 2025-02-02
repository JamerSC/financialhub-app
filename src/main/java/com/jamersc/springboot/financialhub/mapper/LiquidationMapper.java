package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.LiquidationDto;
import com.jamersc.springboot.financialhub.model.Liquidation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PettyCashMapper.class, ClientAccountMapper.class})
public interface LiquidationMapper {

    LiquidationMapper INSTANCE = Mappers.getMapper(LiquidationMapper.class);

    LiquidationDto toLiquidationDto(Liquidation liquidation);

    Liquidation toLiquidationEntity(LiquidationDto liquidationDto);
}
