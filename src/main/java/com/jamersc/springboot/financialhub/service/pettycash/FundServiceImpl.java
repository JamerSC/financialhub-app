package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.repository.FundRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FundServiceImpl implements FundService{

    @Autowired
    private FundRepo fundRepo;

    @Override
    public List<Fund> getAllFund() {
        return fundRepo.findAll();
    }

    @Override
    public Fund getFundById(Long id) {
        return fundRepo.findById(id).orElseThrow(() -> new RuntimeException("Fund id not found."));
    }

    @Override
    public void save(Fund fund) {
       fundRepo.save(fund);
    }
}
