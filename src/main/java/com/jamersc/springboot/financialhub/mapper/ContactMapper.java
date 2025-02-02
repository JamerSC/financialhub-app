package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// @Component
@Mapper(uses = {ContactIndividualMapper.class, ContactCompanyMapper.class, ContactDetailsMapper.class, ClientAccountMapper.class, UserMapper.class})
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactDto toContactDto(Contact contact);

    @Mapping(target = "user", ignore = true)
    Contact toContactEntity(ContactDto contactDto);
}
