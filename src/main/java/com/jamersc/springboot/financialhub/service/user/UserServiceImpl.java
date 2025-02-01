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

    //private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ContactMapper contactMapper;

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> getAllUsersByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            UserDto dto= userMapper.toUserDto(user);

            ContactDto contact = contactMapper.toContactDto(user.getContact());
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
    public void save(UserDto dto, String username) {
        User user = new User();
        User creator = userRepository.findByUsername(username);

        if (dto.getContact() != null) {
            Contact contact = contactMapper.toContactEntity(dto.getContact());
            user.setContact(contact);
        }
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);

        user.setFullName(dto.getFullName());

        if (creator != null) {
            user.setCreatedBy(creator.getUserId());
            user.setUpdatedBy(creator.getUserId());
            //logger.info("Create By: " + creator.getUsername());
            //logger.info("User ID: " + creator.getUserId());
        } else {
            user.setCreatedBy(1L);
            user.setUpdatedBy(1L);
        }
        Set<Role> roles = dto.getRoleIds().stream()
                                .map(roleRepository::findById)
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toSet());
        user.setRoles(roles);
        // BeanUtils.copyProperties(userDto, user, "createdAt");
        BeanUtils.copyProperties(dto, user, "password", "roles");
        userRepository.save(user);
        //logger.info("Successfully created user: " + user.getUsername());
    }

    @Override
    public void update(UserDto dto, String username) {
        User user;
        User updatedBy = userRepository.findByUsername(username);

        if (dto.getUserId() != null) {
            user = userRepository.findById(dto.getUserId()).orElse(new User());

            if (dto.getContact()  != null) {
                Contact contact = contactMapper.toContactEntity(dto.getContact());
                user.setContact(contact);
            }

            user.setFullName(dto.getFullName());

            Set<Role> roles = dto.getRoleIds().stream()
                    .map(roleRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setRoles(roles);

            if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(dto.getPassword());
                user.setPassword(encodedPassword);
            }

            if (updatedBy != null) {
                user.setUpdatedBy(updatedBy.getUserId());
            }

            //logger.info("Updated user details successfully!");
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUserById(Long id) {
        //logger.info("Successfully deleted user record by id: " + id);
        userRepository.deleteById(id);
    }
}
