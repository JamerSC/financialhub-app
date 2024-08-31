package com.jamersc.springboot.financialhub.service.user;

import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpli implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleWithUsers(Long id) {
        return roleRepository.findByIdWithUsers(id).orElseThrow(() -> new RuntimeException("Role not found!"));
    }

    @Override
    public Role findRolesById(Long id) {
        return roleRepository.findById(id).orElseThrow(null);
    }
}
