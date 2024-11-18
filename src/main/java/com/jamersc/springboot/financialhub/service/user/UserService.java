package com.jamersc.springboot.financialhub.service.user;

import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<User> getAllUsers();

    Page<User> findAll(Pageable pageable);

    UserDto findUserRecordById(Long id);

    // Save create user & created by user id session.
    void save(UserDto dto, String username);

    void update(UserDto dto, String username);

    void deleteUserRecordById(Long id);
}
