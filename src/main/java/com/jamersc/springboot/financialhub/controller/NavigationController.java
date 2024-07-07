package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.entity.DummyData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            model.addAttribute("username", auth.getName());
            model.addAttribute("roles", auth.getAuthorities());
        }
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

    @GetMapping("/settings")
    public String settingsPage(Model model) {

        List<DummyData> users = new ArrayList<>();
        users.add(new DummyData("John Doe", 24));
        users.add(new DummyData("Mary Public", 22));
        users.add(new DummyData("Walter White", 23));

        model.addAttribute("people", users);

        return  "settings/settings";
    }
}
