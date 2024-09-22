package com.jamersc.springboot.financialhub.service.user;

import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.RoleRepo;
import com.jamersc.springboot.financialhub.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    @Override
    public UserDto findUserRecordById(Long id) {
        User user = userRepo.findById(id).orElse(null);
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
        User creator = userRepo.findByUsername(createdBy);
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
                                .map(roleRepo::findById)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toSet());
        user.setRoles(roles);
        // BeanUtils.copyProperties(userDto, user, "createdAt");
        BeanUtils.copyProperties(userDto, user, "password", "roles");
        userRepo.save(user);
        logger.info("Successfully created user: " + user.getUsername());
    }

    @Override
    public void update(UserDto userDto, String updatedBy) {
        User user = userRepo.findById(userDto.getId()).orElse(null);
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
                    .map(roleRepo::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setRoles(roles);

            User updater = userRepo.findByUsername(updatedBy);
            if (updater != null) {
                user.setUpdatedBy(Math.toIntExact(updater.getId()));
            } else {
                user.setUpdatedBy(1);
            }

            userRepo.save(user);
            logger.info("Successfully updated user: " + user.getUsername());
        } else {
            logger.warn("User with ID " + userDto.getId() + " not found.");
        }
    }

    @Override
    public void deleteUserRecordById(Long id) {
        logger.info("Successfully deleted user record by id: " + id);
        userRepo.deleteById(id);
    }
}
