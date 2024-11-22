package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.dto.LiquidationDto;
import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import org.springframework.stereotype.Component;

@Component
public class LiquidationMapper {

    // Convert DTO to Entity
    public static Liquidation toLiquidationEntity(LiquidationDto liquidationDto) {
        if (liquidationDto == null) {
            return null;
        }

        PettyCashActivity pettyCashActivity = null;
        if (liquidationDto.getPettyCash() != null) {
            pettyCashActivity = new PettyCashActivity(); // Map fields if PettyCash has fields
            pettyCashActivity.setPcActivityId(liquidationDto.getPettyCash().getPcActivityId());
            // Add other mappings here if PettyCash has more fields
        }

        Contact chargeTo = null;
        if (liquidationDto.getChargeTo() != null) {
            chargeTo = new Contact(); // Map fields if Contact has fields
            chargeTo.setContactId(liquidationDto.getChargeTo().getClient().getContactId());
            // Add other mappings here if Contact has more fields
        }

        Liquidation liquidation = new Liquidation();
        liquidation.setLiquidationId(liquidationDto.getLiquidationId());
        liquidation.setPettyCash(pettyCashActivity);
        liquidation.setDate(liquidationDto.getDate());
        liquidation.setParticulars(liquidationDto.getParticulars());
        liquidation.setCost(liquidationDto.getCost());
        liquidation.setReceiptNo(liquidationDto.getReceiptNo());
        liquidation.setRemarks(liquidationDto.getRemarks());
        liquidation.setChargeTo(chargeTo);
        liquidation.setCreatedBy(liquidationDto.getCreatedBy());
        liquidation.setCreatedAt(liquidationDto.getCreatedAt());
        liquidation.setUpdatedBy(liquidationDto.getUpdatedBy());
        liquidation.setUpdatedAt(liquidationDto.getUpdatedAt());

        return liquidation;
    }

    // Convert Entity to DTO
    public static LiquidationDto toLiquidationDto(Liquidation liquidation) {
        if (liquidation == null) {
            return null;
        }

        PettyCashActivityDto pettyCashActivityDto = null;
        if (liquidation.getPettyCash() != null) {
            pettyCashActivityDto = new PettyCashActivityDto(); // Map fields if PettyCashDto has fields
            pettyCashActivityDto.setPcActivityId(liquidation.getPettyCash().getPcActivityId());
            // Add other mappings here if PettyCashDto has more fields
        }

        ClientAccountDto chargeToDto = null;
        if (liquidation.getChargeTo() != null) {
            chargeToDto = new ClientAccountDto(); // Map fields if ClientAccountDto has fields
            chargeToDto.setClient(ContactMapper.toContactDto(liquidation.getChargeTo()));
            // Add other mappings here if ClientAccountDto has more fields
        }

        return new LiquidationDto(
                liquidation.getLiquidationId(),
                pettyCashActivityDto,
                liquidation.getDate(),
                liquidation.getParticulars(),
                liquidation.getCost(),
                liquidation.getReceiptNo(),
                liquidation.getRemarks(),
                chargeToDto,
                liquidation.getCreatedBy(),
                liquidation.getCreatedAt(),
                liquidation.getUpdatedBy(),
                liquidation.getUpdatedAt()
        );
    }
}
