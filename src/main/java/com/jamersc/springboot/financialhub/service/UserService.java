package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<User> getAllUsers();
}
