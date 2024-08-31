package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUsername(String username);
    Page<User> findAll(Pageable pageable);
}
