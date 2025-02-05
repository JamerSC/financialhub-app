package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.LiquidationDto;
import com.jamersc.springboot.financialhub.model.Liquidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {PettyCashMapper.class, ClientAccountMapper.class})
public interface LiquidationMapper {

    LiquidationMapper INSTANCE = Mappers.getMapper(LiquidationMapper.class);

    @Mapping(target = "pettyCashActivityId", source = "liquidation.activityId")
    LiquidationDto toLiquidationDto(Liquidation liquidation);

    @Mapping(target = "activity", ignore = true)
    Liquidation toLiquidationEntity(LiquidationDto liquidationDto);
}
