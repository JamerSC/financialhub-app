package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.ClientAccount;
import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = {ContactMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "confirmPassword", ignore = true)
    @Mapping(target = "contact", source = "contact", qualifiedByName = "toContactDto")
    @Mapping(target = "roleIds", source = "roles", qualifiedByName = "mapRolesToRoleIds")
    UserDto toUserDto(User user);

    @Mapping(target = "pettyCash", ignore = true)
    @Mapping(target = "roles", source = "roleIds",  qualifiedByName = "mapRoleIdsToRoles")
    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    User toUserEntity(UserDto userDto);

    @Named("mapRolesToRoleIds")
    default Set<Long> mapRolesToRoleIds(Set<Role> roles) {
        return roles != null ? roles.stream().map(Role::getId).collect(Collectors.toSet()) : null;
    }

    @Named("mapRoleIdsToRoles")
    default Set<Role> mapRoleIdsToRoles(Set<Long> roleIds) {
        return roleIds != null ? roleIds.stream().map(id -> {
            Role role = new Role();
            role.setId(id);
            return role;
        }).collect(Collectors.toSet()) : null;
    }

    @Named("toContactDto")
    @Mapping(target = "clientAccounts", source = "clientAccounts", qualifiedByName = "toClientAccountDto")
    @Mapping(target = "user", ignore = true) // FIX: Ignore unmapped 'user' field
    default ContactDto toContactDto(Contact contact) {
        if (contact == null) return null;
        ContactDto dto;
        dto = new ContactDto();
        // Map other fields
        return dto;
    }

    @Named("toContact")
    @Mapping(target = "clientAccounts", source = "clientAccounts", qualifiedByName = "toClientAccount")
    default Contact toContact(ContactDto dto) {
        if (dto == null) return null;
        Contact contact;
        contact = new Contact();
        // Map other fields
        return contact;
    }

    @Named("toClientAccountDto")
    default ClientAccountDto toClientAccountDto(ClientAccount account) {
        if (account == null) return null;
        ClientAccountDto dto;
        dto = new ClientAccountDto();
        // Map fields manually
        return dto;
    }

    @Named("toClientAccount")
    default ClientAccount toClientAccount(ClientAccountDto dto) {
        if (dto == null) return null;
        ClientAccount account;
        account= new ClientAccount();
        // Map fields manually
        return account;
    }
}
