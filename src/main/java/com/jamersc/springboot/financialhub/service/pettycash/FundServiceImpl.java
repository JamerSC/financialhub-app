package com.jamersc.springboot.financialhub.service.pettycash;

import com.jamersc.springboot.financialhub.dto.FundDto;
import com.jamersc.springboot.financialhub.mapper.FundMapper;
import com.jamersc.springboot.financialhub.model.Fund;
import com.jamersc.springboot.financialhub.repository.FundRepository;
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
    private FundRepository fundRepository;

    @Override
    public List<Fund> getAllFund() {
        return fundRepository.findAll();
    }

    @Override
    public FundDto getFundById(Long id) {
        Fund fund = fundRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fund id not found."));
        return FundMapper.toFundDto(fund);
    }

    @Override
    public void save(Fund fund) {
       fundRepository.save(fund);
    }
}
