package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    User findByUsername(String username);
}
