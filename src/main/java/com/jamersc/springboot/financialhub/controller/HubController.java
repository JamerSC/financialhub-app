package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.check.CheckService;
import com.jamersc.springboot.financialhub.service.pettycash.PettyCashService;
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
    public String showLoginPage() {
        return "login/login"; // This should correspond to a Thymeleaf template named 'login.html'
    }

    @GetMapping("/dashboard")
    public String adminDashboardPage(Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size) {
        model.addAttribute("totalOfUsers", userService.getAllUsers());
        model.addAttribute("totalOfPettyCash", pettyCashService.getAllPettyCash());
        model.addAttribute("totalOfChecks", checkService.getAllCheckRecord());

        Page<User> usersPage = userService.findAll(PageRequest.of(page, size));
        List<User> users = usersPage.getContent();
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("pageSize", size);  // Send pageSize to Thymeleaf for the selected option

        return "dashboard/dashboard";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        logger.warn("Access denied!");
        return "access-denied";
    }

    /*@GetMapping("/error")
    public String error() {
        logger.error("Error!");
        return "error";
    }*/
}
