package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByUsername(String username);
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);
}
