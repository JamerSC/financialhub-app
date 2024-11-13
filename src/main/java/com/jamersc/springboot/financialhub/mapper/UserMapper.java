package com.jamersc.springboot.financialhub.mapper;

import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toUserEntity(UserDto userDto, Contact contact, Set<Role> roles) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setContact(contact);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword()); // Ideally, password should be hashed
        user.setEnabled(userDto.getEnabled());
        user.setRoles(roles);
        user.setCreatedAt(new Date());

        return user;
    }

    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setContact(ContactMapper.toContactDto(user.getContact()));
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setMiddleName(user.getMiddleName());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setEnabled(user.getEnabled());
        userDto.setRoleIds(
                user.getRoles()
                .stream()
                .map(Role::getId)
                .collect(Collectors.toSet())
        );

        return userDto;
    }
}
