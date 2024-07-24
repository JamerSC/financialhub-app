package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRoleWithUsers(Long id);
}
