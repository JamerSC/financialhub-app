package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Check;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.check.CheckService;
import com.jamersc.springboot.financialhub.service.cash.PettyCashService;
import com.jamersc.springboot.financialhub.service.user.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String adminDashboardPage(Model model, @RequestParam(defaultValue = "0") int page) {
        model.addAttribute("totalOfUsers", userService.getAllUsers());
        model.addAttribute("totalOfPettyCash", pettyCashService.getAllPettyCashRecord());
        model.addAttribute("totalOfChecks", checkService.getAllCheckRecord());
        Page<User> usersPage = userService.findAll(PageRequest.of(page, 6));
        List<User> users = usersPage.getContent();
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());

        return "dashboard/dashboard";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        logger.error("Access denied!");
        return "access-denied";
    }
}
