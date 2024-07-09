package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.entity.DummyData;
import com.jamersc.springboot.financialhub.service.UserService;
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

@Controller
@RequestMapping("/financial-hub")
public class NavigationController {

    @Autowired
    private UserService userService;

    public static final String USER_LOGIN = "userLogin  ";
    public static final String USERS_ROLES = "usersRoles";

    @GetMapping("/login")
    public String financialHubLoginPage(Model model) {
        return "login/login";
    }

    @GetMapping("/dashboard")
    public String adminDashboardPage(Model model,
                                     @AuthenticationPrincipal UserDetails userDetails) {
/*
        String username = userDetails.getUsername();
        System.out.println("Username: " + userDetails.getUsername());
        model.addAttribute(USER_LOGIN, username);

        String roles = String.valueOf(userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        System.out.println("Role(s): " + userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        model.addAttribute(USERS_ROLES, roles);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authenticated: " + auth);

        if (auth != null) {
            model.addAttribute("username", auth.getName());
            model.addAttribute("roles", auth.getAuthorities());
            System.out.println("Get name: " + auth.getName());
            System.out.println("Get Authorities" + auth.getAuthorities());
        }*/
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
