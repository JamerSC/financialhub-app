package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;

import java.util.List;

public interface PettyCashService {

    List<PettyCashDto> getAllPettyCash();

    // Petty Cash Controller
    List<PettyCash> getUnapprovedPettyCashByReceivedBy(User user);

    // MyActivity Controller
    List<PettyCash> getApprovedPettyCashByReceivedBy(User user);

    List<PettyCash> getByClientAccountId(Long id);

    List<PettyCash> getAllPettyCashWithClientAccounts();

    PettyCashDto getPettyCashById(Long id);

    PettyCash getPettyCashLiquidationById(Long id);

    void savePettyCash(PettyCashDto dto, String username);

    void saveAdminPettyCash(PettyCashDto dto, String username);

    void save(PettyCash pettyCash);

    void deletePettyCashRecordById(Long id);
}
