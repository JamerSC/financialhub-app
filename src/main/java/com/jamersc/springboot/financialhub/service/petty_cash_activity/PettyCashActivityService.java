package com.jamersc.springboot.financialhub.service.petty_cash_activity;

import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import com.jamersc.springboot.financialhub.model.User;

import java.util.List;

public interface PettyCashActivityService {

    List<PettyCashActivityDto> getAllPettyCash();
    // Petty Cash Controller
    List<PettyCashActivity> getUnapprovedPettyCashByReceivedBy(User user);
    // MyActivity Controller
    List<PettyCashActivity> getApprovedPettyCashByReceivedBy(User user);
    List<PettyCashActivity> getByClientAccountId(Long id);
    List<PettyCashActivity> getAllPettyCashWithClientAccounts();
    PettyCashActivityDto getPettyCashById(Long id);
    PettyCashActivity getPettyCashLiquidationById(Long id);
    void savePettyCash(PettyCashActivityDto dto, String username);
    void saveAdminPettyCash(PettyCashActivityDto dto, String username);
    void save(PettyCashActivity pettyCash);
    void deletePettyCashRecordById(Long id);
}
