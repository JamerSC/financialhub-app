package com.jamersc.springboot.financialhub.service.user;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.mapper.ContactMapper;
import com.jamersc.springboot.financialhub.mapper.UserMapper;
import com.jamersc.springboot.financialhub.model.Contact;
import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.repository.ContactRepository;
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
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDto findUserRecordById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            UserDto dto= UserMapper.toUserDto(user);

            ContactDto contact = ContactMapper.toContactDto(user.getContact());
            dto.setContact(contact);
            // Populate roleIds
            Set<Long> roleIds = user.getRoles().stream()
                    .map(Role::getId).
                    collect(Collectors.toSet());
            dto.setRoleIds(roleIds);

            return dto;
        }
        return null;
    }

    @Override
    public void save(UserDto userDto, String username) {
        User user = new User();
        User creator = userRepository.findByUsername(username);

        if (userDto.getContact() != null) {
            Contact contact = ContactMapper.toContactEntity(userDto.getContact());
            user.setContact(contact);
        }
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        if (creator != null) {
            user.setCreatedBy(creator.getUserId());
            user.setUpdatedBy(creator.getUserId());
            logger.info("Create By: " + creator.getUsername());
            logger.info("User ID: " + creator.getUserId());
        } else {
            user.setCreatedBy(1L);
            user.setUpdatedBy(1L);
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
    public void update(UserDto userDto, String username) {
        User user;
        User updatedBy = userRepository.findByUsername(username);

        if (userDto.getUserId() != null) {
            user = userRepository.findById(userDto.getUserId()).orElse(new User());

            if (userDto.getContact()  != null) {
                Contact contact = ContactMapper.toContactEntity(userDto.getContact());
                user.setContact(contact);
            }

            user.setFirstName(userDto.getFirstName());
            user.setMiddleName(userDto.getMiddleName());
            user.setLastName(userDto.getLastName());

            Set<Role> roles = userDto.getRoleIds().stream()
                    .map(roleRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setRoles(roles);

            if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(userDto.getPassword());
                user.setPassword(encodedPassword);
            }

            if (updatedBy != null) {
                user.setUpdatedBy(updatedBy.getUserId());
            }

            logger.info("Updated user details successfully!");
            userRepository.save(user);
        }



        /*User user = userRepository.findById(userDto.getUserId()).orElse(null);
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
                user.setUpdatedBy(updater.getUserId());
            } else {
                user.setUpdatedBy(1L);
            }

            userRepository.save(user);
            logger.info("Successfully updated user: " + user.getUsername());
        } else {
            logger.warn("User with ID " + userDto.getUserId() + " not found.");
        }*/
    }

    @Override
    public void deleteUserRecordById(Long id) {
        logger.info("Successfully deleted user record by id: " + id);
        userRepository.deleteById(id);
    }
}
