package com.jamersc.springboot.financialhub.repository;

import com.jamersc.springboot.financialhub.model.ProjectAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectAccountRepository extends JpaRepository<ProjectAccount, Long> {
    //
}
