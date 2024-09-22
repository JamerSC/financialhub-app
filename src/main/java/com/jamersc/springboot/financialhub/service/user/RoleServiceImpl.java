package com.jamersc.springboot.financialhub.service.user;

import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.repository.RoleRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role getRoleWithUsers(Long id) {
        return roleRepo.findByIdWithUsers(id).orElseThrow(() -> new RuntimeException("Role not found!"));
    }

    @Override
    public Role findRolesById(Long id) {
        return roleRepo.findById(id).orElseThrow(null);
    }
}
