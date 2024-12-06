package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.LiquidationDto;
import com.jamersc.springboot.financialhub.model.Liquidation;

public class LiquidationMapper {

    public static LiquidationDto liquidationToDto(Liquidation liquidation) {
        if (liquidation == null) {
            return null;
        }

        LiquidationDto dto = new LiquidationDto();
        dto.setActivityId(liquidation.getActivityId());
        dto.setPettyCash(PettyCashMapper.toPettyCashDto(liquidation.getActivity()));
        dto.setDate(liquidation.getDate());
        dto.setParticulars(liquidation.getParticulars());
        dto.setCost(liquidation.getCost());
        dto.setReceiptNo(liquidation.getReceiptNo());
        dto.setRemarks(liquidation.getRemarks());
        dto.setChargeTo(ClientAccountMapper.toClientAccountDto(liquidation.getChargeTo()));
        dto.setCreatedBy(liquidation.getCreatedBy());
        dto.setCreatedAt(liquidation.getCreatedAt());
        dto.setUpdatedBy(liquidation.getUpdatedBy());
        dto.setUpdatedAt(liquidation.getUpdatedAt());
        return dto;
    }

    public static Liquidation liquidationToEntity(LiquidationDto dto) {
        if (dto == null) {
            return null;
        }

        Liquidation liquidation = new Liquidation();
        liquidation.setActivityId(dto.getActivityId());
        // Populate the `activity` and other nested objects manually or through another mapper
        liquidation.setDate(dto.getDate());
        liquidation.setParticulars(dto.getParticulars());
        liquidation.setCost(dto.getCost());
        liquidation.setReceiptNo(dto.getReceiptNo());
        liquidation.setRemarks(dto.getRemarks());
        // Fetch the `ClientAccount` entity based on `clientAccountId` if necessary
        liquidation.setCreatedBy(dto.getCreatedBy());
        liquidation.setCreatedAt(dto.getCreatedAt());
        liquidation.setUpdatedBy(dto.getUpdatedBy());
        liquidation.setUpdatedAt(dto.getUpdatedAt());
        return liquidation;
    }
}
