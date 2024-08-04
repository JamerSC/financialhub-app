package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.RoleService;
import com.jamersc.springboot.financialhub.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/settings")
public class SettingsController {

    private static final Logger logger = LoggerFactory.getLogger(HubController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/user-settings")
    public String userSettingsPage(Model model) {
        addUsersToModel(model);
        return  "settings/user-settings";
    }

    @GetMapping("/user-settings-create-form")
    public String createUserFormPage(Model model) {
        addRolesToModel(model);
        model.addAttribute("userDto", new UserDto());
        return "settings/create-user-form";
    }

    @PostMapping("/user-settings-create-form")
    public String processCreateUserForm(@Valid @ModelAttribute("userDto") UserDto userDto,
                                     BindingResult result, Model model) {
        String username = userDto.getUsername();
        logger.info("Processing registration form for: " + username);
        if (result.hasErrors()) {
            addUsersToModel(model);
            addRolesToModel(model);
            return "settings/create-user-form";
        }
        // session ID of the user who creates a new user using Spring Security's SecurityContextHolder to get the current user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionUsername = authentication.getName();
        User existingUsername = userService.findByUsername(username);
        if(existingUsername != null) {
            addUsersToModel(model);
            result.rejectValue("username", "error.username",
                    "Invalid! Username '" + username + "' already exist!");
            addRolesToModel(model);
            model.addAttribute("formHasErrors", true);
            logger.warn("Username already exists!");
            return "settings/create-user-form";
        }
        userService.save(userDto, sessionUsername);
        logger.info("Successfully created user: " + username);
        return  "redirect:/settings/user-settings";
    }

    @GetMapping("/user-settings-update-form/{id}")
    public String userSettingsUpdateForm(@PathVariable(value = "id") Long id, Model model) {
        UserDto userDto = userService.findUserRecordById(id);
        if (userDto != null) {
            model.addAttribute("userDto", userDto);
            addRolesToModel(model);
            return "settings/update-user-form";
        }
        return "redirect:/settings/user-settings";
    }

    @PostMapping("/user-settings-update-form")
    public String processUpdateUserRecord(@Valid @ModelAttribute("userDto") UserDto userDto,
                                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Error! Please complete all required fields.");
            addRolesToModel(model);
            return "settings/update-user-form";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String updatedBy = authentication.getName();
        // Check if the user exists
        UserDto existingUser = userService.findUserRecordById(userDto.getId());
        if (existingUser == null) {
            logger.error("User not found with id: " + userDto.getId());
            return "redirect:/settings/user-settings";
        }
        // Check if the username has been updated
        if (!existingUser.getUsername().equals(userDto.getUsername())) {
            User existingUsername = userService.findByUsername(userDto.getUsername());
            if (existingUsername != null) {
                result.rejectValue("username", "error.username",
                        "Invalid! Username '" + userDto.getUsername() + "' already exists!");
                addRolesToModel(model);
                logger.warn("Username already exists!");
                return "settings/update-user-form";
            }
        }
        userService.update(userDto, updatedBy);
        logger.info("Updated user id: " + userDto.getId());
        return "redirect:/settings/user-settings";
    }

    @GetMapping("/delete-user-record/{id}")
    public String deleteUserRecordById(@PathVariable(value = "id") Long id) {
        logger.info("Process deleting user id: " + id);
        userService.deleteUserRecordById(id);
        logger.info("Successfully deleted user record id" + id);
        return "redirect:/settings/user-settings";
    }

    private void addUsersToModel(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        logger.info("Get all users:\n" + users);
    }

    private void addRolesToModel(Model model) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
    }
}
