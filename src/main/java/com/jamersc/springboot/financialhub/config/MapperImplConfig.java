package com.jamersc.springboot.financialhub.config;

import com.jamersc.springboot.financialhub.mapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperImplConfig {
    @Bean
    public BankAccountMapper bankAccountMapper() {
        return new BankAccountMapperImpl();
    }
    @Bean
    public BankMapper bankMapper() {
        return new BankMapperImpl();
    }
    @Bean
    public BankTransactionMapper bankTransactionMapper() {
        return new BankTransactionMapperImpl();
    }
    @Bean
    public CaseAccountMapper caseAccountMapper() {
        return new CaseAccountMapperImpl();
    }
    @Bean
    public ClientAccountMapper clientAccountMapper() {
        return new ClientAccountMapperImpl();
    }
    @Bean
    public ContactCompanyMapper contactCompanyMapper() {
        return new ContactCompanyMapperImpl();
    }
    @Bean
    public ContactDetailsMapper contactDetailsMapper() {
        return new ContactDetailsMapperImpl();
    }
    @Bean
    public ContactIndividualMapper contactIndividualMapper() {
        return new ContactIndividualMapperImpl();
    }
    @Bean
    public ContactMapper contactMapper() {
        return new ContactMapperImpl();
    }
    @Bean
    public FundMapper fundMapper() {
        return new FundMapperImpl();
    }
    @Bean
    public LiquidationMapper liquidationMapper() {
        return new LiquidationMapperImpl();
    }
    @Bean
    public PettyCashMapper pettyCashMapper() {
        return new PettyCashMapperImpl();
    }
    @Bean
    public ProjectAccountMapper projectAccountMapper() {
        return new ProjectAccountMapperImpl();
    }
    @Bean
    public RetainerAccountMapper retainerAccountMapper() {
        return new RetainerAccountMapperImpl();
    }
    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }
}
