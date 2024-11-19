package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.ContactDto;
import com.jamersc.springboot.financialhub.dto.UserDto;
import com.jamersc.springboot.financialhub.model.Role;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.contact.ContactService;
import com.jamersc.springboot.financialhub.service.user.RoleService;
import com.jamersc.springboot.financialhub.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ContactService contactService;

    @GetMapping("/users")
    public String usersManagementPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Page<User> usersPage = userService.getAllUsersByPage(PageRequest.of(page, 10));
        List<User> users = usersPage.getContent();
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        return  "settings/user-settings";
    }

    @GetMapping("/user-settings-create-form")
    public String createUsersAccountPage(Model model) {
        addRolesToModel(model);
        addContactsNewUserToModel(model);
        model.addAttribute("userDto", new UserDto());
        return "settings/create-user-form";
    }

    @PostMapping("/user-settings-create-form")
    public String processCreateUsersAccount(@Valid @ModelAttribute("userDto") UserDto userDto,
                                     BindingResult result, Model model) {
        String username = userDto.getUsername();
        logger.info("Processing registration form for: " + username);
        if (result.hasErrors()) {
            addRolesToModel(model);
            addContactsNewUserToModel(model);
            return "settings/create-user-form";
        }
        User existingUsername = userService.getByUsername(username);
        if(existingUsername != null) {
            addUsersToModel(model);
            result.rejectValue("username", "error.username",
                    "Invalid! Username '" + username + "' already exist!");
            addRolesToModel(model);
            addContactsNewUserToModel(model);
            model.addAttribute("formHasErrors", true);
            logger.warn("Username already exists!");
            return "settings/create-user-form";
        }
        String createdBy = getSessionUsername();
        userService.save(userDto, createdBy);
        logger.info("Successfully created user: " + username);
        return  "redirect:/settings/users";
    }

    @GetMapping("/user-settings-update-form/{id}")
    public String updateUsersAccountPage(@PathVariable(value = "id") Long id, Model model) {
        UserDto userDto = userService.findUserById(id);
        if (userDto != null) {
            model.addAttribute("userDto", userDto);
            addRolesToModel(model);
            addContactsExistingUserToModel(model);
            return "settings/update-user-form";
        }
        return "redirect:/settings/users";
    }

    @PostMapping("/user-settings-update-form")
    public String processUpdateUsersAccount(@Valid @ModelAttribute("userDto") UserDto userDto,
                                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            logger.error("Error! Please complete all required fields.");
            addRolesToModel(model);
            addContactsExistingUserToModel(model);
            return "settings/update-user-form";
        }
        String updatedBy = getSessionUsername();
        UserDto existingUser = userService.findUserById(userDto.getUserId());
        if (existingUser == null) {
            logger.error("User not found with id: " + userDto.getUserId());
            return "redirect:/settings/users";
        }
        // Check if the username has been updated
        if (!existingUser.getUsername().equals(userDto.getUsername())) {
            User existingUsername = userService.getByUsername(userDto.getUsername());
            if (existingUsername != null) {
                result.rejectValue("username", "error.username",
                        "Invalid! Username '" + userDto.getUsername() + "' already exists!");
                addRolesToModel(model);
                addContactsExistingUserToModel(model);
                logger.warn("Username already exists!");
                return "settings/update-user-form";
            }
        }
        userService.update(userDto, updatedBy);
        return "redirect:/settings/users";
    }

    @GetMapping("/delete-user-record/{id}")
    public String deleteUsersAccountById(@PathVariable(value = "id") Long id) {
        logger.info("Process deleting user id: " + id);
        userService.deleteUserById(id);
        return "redirect:/settings/users";
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

    private void addContactsNewUserToModel(Model model) {
        List<ContactDto> internalContactsNewUser = contactService.findContactsWithInternalCategoryAndNullUser();
        model.addAttribute("internalContactsNewUser", internalContactsNewUser);
    }

    private void addContactsExistingUserToModel(Model model) {
        List<ContactDto> internalContactsWithUserAccess = contactService.findContactsWithInternalCategoryAndNotNullUser();
        model.addAttribute("internalContactsWithUserAccess", internalContactsWithUserAccess);
    }

    private String getSessionUsername() {
        // session ID of the user who creates a new user using Spring Security's SecurityContextHolder to get the current user's details
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
