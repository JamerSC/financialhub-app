package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<User> getAllUsers();

    // Save create user & created by user id session.
    void save(UserDto userDto, String sessionName);
}
