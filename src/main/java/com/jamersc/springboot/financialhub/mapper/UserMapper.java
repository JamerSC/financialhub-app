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

    public static UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setContact(ContactMapper.toContactDto(user.getContact()));
        userDto.setFullName(user.getFullName());
        userDto.setUsername(user.getUsername());
        userDto.setEnabled(user.getEnabled());
        userDto.setCreatedBy(user.getCreatedBy());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedBy(user.getUpdatedBy());
        userDto.setUpdatedAt(user.getUpdatedAt());
        userDto.setRoleIds(
                user.getRoles()
                .stream()
                .map(Role::getId)
                .collect(Collectors.toSet())
        );

        return userDto;
    }

    public static User toUserEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User user = new User();
        user.setUserId(userDto.getUserId());
        //user.setContact(contact);
        user.setFullName(userDto.getFullName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword()); // Ideally, password should be hashed
        user.setEnabled(userDto.getEnabled());
        user.setCreatedBy(userDto.getCreatedBy());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUpdatedBy(userDto.getUpdatedBy());
        user.setUpdatedAt(userDto.getUpdatedAt());
        //user.setRoles(roles);
        user.setCreatedAt(new Date());

        return user;
    }
}
