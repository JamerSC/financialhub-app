package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.dto.PettyCashDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;

import java.util.List;

public interface PettyCashService {

    List<PettyCashDto> getAllPettyCash();

    List<PettyCash> getPettyCashByUserRole(User user);

    List<PettyCash> getAllPettyCashWithClientAccounts();

    PettyCashDto getPettyCashById(Long id);

    PettyCash getPettyCashLiquidationById(Long id);

    void savePettyCash(PettyCashDto dto, String username);

    void saveAdminPettyCash(PettyCashDto dto, String username);

    void save(PettyCash pettyCash);

    void deletePettyCashRecordById(Long id);
}
