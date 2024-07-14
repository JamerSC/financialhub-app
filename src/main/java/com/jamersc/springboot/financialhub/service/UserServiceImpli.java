package com.jamersc.springboot.financialhub.service;

import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.RoleRepository;
import com.jamersc.springboot.financialhub.repository.UserRepository;
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
    public void save(UserDto userDto, String sessionName) {
        User user = new User();
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(userDto.isEnabled());

        User createdBy = userRepository.findByUsername(sessionName);
        if (createdBy != null) {
            user.setCreatedBy(Math.toIntExact(createdBy.getId()));
            user.setUpdatedBy(Math.toIntExact(createdBy.getId()));
            logger.info("Create By: " + createdBy.getUsername());
            logger.info("User ID: " + createdBy.getId());
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
}
