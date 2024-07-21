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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/financial-hub")
public class NavigationController {

    private static final Logger logger = LoggerFactory.getLogger(NavigationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public String financialHubLoginPage(Model model) {
        return "login/login";
    }

    @GetMapping("/dashboard")
    public String adminDashboardPage(Model model) {
        // display all users from database.
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "dashboard/dashboard";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

/*    @GetMapping("/error")
    public String errorOccur() {
        return "error";
    }*/

    @GetMapping("/user-settings")
    public String settingsPage(Model model) {
        // display all users from database.
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        // fetch all roles
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        // new model for create user dto
        model.addAttribute("userDto", new UserDto());
        return  "settings/user-settings";
    }

    @PostMapping("/user-settings")
    public String settingsCreateUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                                     BindingResult result, Model model) {
        // logging info
        String username = userDto.getUsername();
        logger.info("Processing registration form for: " + username);
        // session ID of the user who creates a new user using Spring Security's SecurityContextHolder to get the current user's details
        // Get current session name of user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionUsername = authentication.getName();

        if (result.hasErrors()) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            //model.addAttribute("formHasErrors", true);
            return "settings/user-settings";
        }
        User existingUser = userService.findByUsername(username);
        if(existingUser != null) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("users", users);
            result.rejectValue("username", "error.username",
                    "Invalid! Username '" + username + "' already exist!");
            List<Role> roles = roleService.getAllRoles();
            model.addAttribute("roles", roles);
            //model.addAttribute("formHasErrors", true);
            logger.warn("Username already exists!");
            return "settings/user-settings";
        }
        userService.save(userDto, sessionUsername);
        logger.info("Successfully created user: " + username);
        return  "redirect:/financial-hub/user-settings";
    }
}
