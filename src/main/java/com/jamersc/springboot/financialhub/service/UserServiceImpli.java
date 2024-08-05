package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.RoleRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
@Service
@Transactional
public class UserServiceImpli implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpli.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDto findUserRecordById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user, userDto);
            // Populate roleIds
            Set<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
            userDto.setRoleIds(roleIds);
            return userDto;
        }
        return null;
    }

    @Override
    public void save(UserDto userDto, String createdBy) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(userDto.isEnabled());
        User creator = userRepository.findByUsername(createdBy);
        if (creator != null) {
            user.setCreatedBy(Math.toIntExact(creator.getId()));
            user.setUpdatedBy(Math.toIntExact(creator.getId()));
            logger.info("Create By: " + creator.getUsername());
            logger.info("User ID: " + creator.getId());
        } else {
            user.setCreatedBy(1);
            user.setUpdatedBy(1);
        }
        Set<Role> roles = userDto.getRoleIds().stream()
                                .map(roleRepository::findById)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toSet());
        user.setRoles(roles);
        // BeanUtils.copyProperties(userDto, user, "createdAt");
        BeanUtils.copyProperties(userDto, user, "password", "roles");
        userRepository.save(user);
        logger.info("Successfully created user: " + user.getUsername());
    }

    @Override
    public void update(UserDto userDto, String updatedBy) {
        User user = userRepository.findById(userDto.getId()).orElse(null);
        if (user != null) {
            // Update user details except password and roles
            BeanUtils.copyProperties(userDto, user, "password", "roles", "createdBy", "createdAt");
            // Update password if provided
            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(userDto.getPassword());
                user.setPassword(encodedPassword);
            }
            // Update roles
            Set<Role> roles = userDto.getRoleIds().stream()
                    .map(roleRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setRoles(roles);

            User updater = userRepository.findByUsername(updatedBy);
            if (updater != null) {
                user.setUpdatedBy(Math.toIntExact(updater.getId()));
            } else {
                user.setUpdatedBy(1);
            }

            userRepository.save(user);
            logger.info("Successfully updated user: " + user.getUsername());
        } else {
            logger.warn("User with ID " + userDto.getId() + " not found.");
        }
    }

    @Override
    public void deleteUserRecordById(Long id) {
        logger.info("Successfully deleted user record by id: " + id);
        userRepository.deleteById(id);
    }
}
