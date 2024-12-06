package com.jamersc.springboot.financialhub.service.petty_cash_activity;

import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import com.jamersc.springboot.financialhub.repository.LiquidationRepository;
import com.jamersc.springboot.financialhub.repository.PettyCashActivityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Transactional
@Service
public class LiquidationServiceImpl implements LiquidationService {
    //
    @Autowired
    private LiquidationRepository liquidationRepository;

    @Autowired
    private PettyCashActivityRepository pettyCashActivityRepository;


    @Override
    public List<Liquidation> getAllActivityEntriesByPettyCashActivityId(Long id) {
        return liquidationRepository.findAllActivityEntriesByPettyCashActivityId(id);
    }

    @Override
    public Liquidation findLiquidationById(Long id) {
        return liquidationRepository.findById(id).orElseThrow(null);
    }

    @Override
    public void save(Liquidation liquidation) {
        if (liquidation.getActivity() != null && liquidation.getActivity().getPcActivityId() != null) {
            // Ensure PettyCash is managed
            PettyCashActivity managedPettyCash = pettyCashActivityRepository.getReferenceById(liquidation.getActivity().getPcActivityId());
            liquidation.setActivity(managedPettyCash);
        }
        liquidationRepository.save(liquidation);
    }

    @Override
    public void deleteLiquidationItemById(Long id) {
        liquidationRepository.deleteById(id);
    }
}
