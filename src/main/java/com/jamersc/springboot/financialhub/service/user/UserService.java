package com.jamersc.springboot.financialhub.service.user;

import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<User> getAllUsers();

    UserDto findUserRecordById(Long id);

    // Save create user & created by user id session.
    void save(UserDto userDto, String createdBy);

    void update(UserDto userDto, String updatedBy);

    void deleteUserRecordById(Long id);
}
