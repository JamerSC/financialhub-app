package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.DummyData;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/financial-hub")
public class NavigationController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String financialHubLoginPage(Model model) {
        return "login/login";
    }

    @GetMapping("/dashboard")
    public String adminDashboardPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return  "dashboard/dashboard";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/error")
    public String errorOccur() {
        return "error";
    }

    @GetMapping("/petty-cash")
    public String pettyCashVoucherPage(Model model) {

        model.addAttribute("people", Arrays.asList(
                new DummyData ("John Doe", 1000),
                new DummyData ("Mary Public", 1500),
                new DummyData ("Mary Public", 1500)
        ));

        return  "cash/petty-cash";
    }

    @GetMapping("/check")
    public String checkVoucherPage(Model model) {

        model.addAttribute("banks", List.of("PNB", "BDO", "BPI"));
        return  "check/check";
    }

    @GetMapping("/credit-card")
    public String creditCardVoucherPage(Model model) {
        return  "credit-card/credit-card";
    }

    @GetMapping("/user-settings")
    public String settingsPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return  "settings/user-settings";
    }
}
