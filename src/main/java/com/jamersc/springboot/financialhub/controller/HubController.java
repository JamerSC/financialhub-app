package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Check;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.CheckService;
import com.jamersc.springboot.financialhub.service.PettyCashService;
import com.jamersc.springboot.financialhub.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/financial-hub")
public class HubController {

    private static final Logger logger = LoggerFactory.getLogger(HubController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PettyCashService pettyCashService;

    @Autowired
    private CheckService checkService;

    @GetMapping("/login")
    public String financialHubLoginPage(Model model) {
        return "login/login";
    }

    @GetMapping("/dashboard")
    public String adminDashboardPage(Model model) {
        // display all users from database.
        List<PettyCash> pettyCash = pettyCashService.getAllPettyCashRecord();
        List<Check> checks = checkService.getAllCheckRecord();
        List<User> users = userService.getAllUsers();
        model.addAttribute("checks", checks);
        model.addAttribute("pettyCash", pettyCash);
        model.addAttribute("users", users);
        return "dashboard/dashboard";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        logger.error("Access denied!");
        return "access-denied";
    }
}
