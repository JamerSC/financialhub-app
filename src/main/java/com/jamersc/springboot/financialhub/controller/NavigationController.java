package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/financial-hub")
public class NavigationController {

    @GetMapping("/login")
    public String financialHubLoginPage(Model model) {
        return "login/login";
    }

    @GetMapping("/dashboard")
    public String adminDashboardPage(Model model) {
        return  "dashboard/dashboard";
    }

    @GetMapping("/petty-cash")
    public String pettyCashVoucherPage(Model model) {

        model.addAttribute("people", Arrays.asList(
                new User("John Doe", 1000),
                new User("Mary Public", 1500),
                new User("Mary Public", 1500)
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

    @GetMapping("/settings")
    public String settingsPage(Model model) {

        List<User> users = new ArrayList<>();
        users.add(new User("John Doe", 24));
        users.add(new User("Mary Public", 22));
        users.add(new User("Walter White", 23));

        model.addAttribute("people", users);

        return  "settings/settings";
    }
}
