package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {
}
