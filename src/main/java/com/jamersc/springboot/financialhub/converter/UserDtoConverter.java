package com.jamersc.springboot.financialhub.converter;

import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Converter <String, UserDto> {
    @Autowired
    private UserService userService;

    @Override
    public UserDto convert(String source) {
        if (source.isEmpty()) {
            return null;
        }

        Long userId = Long.valueOf(source);
        return userService.findUserById(userId);
    }
}
