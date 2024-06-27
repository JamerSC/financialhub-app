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

    @GetMapping("/")
    public String showHome(Model model) {
        return  "index";
    }

    @GetMapping("/petty-cash")
    public String showPettyCash(Model model) {

//        User user1 = new User("John Doe", 1000);
//        User user2 = new User("Mary Public", 1500);
//        User user3 = new User("Walter White", 1000);
//        model.addAttribute("people", List.of(user1, user2, user3));

        model.addAttribute("people", Arrays.asList(
                new User("John Doe", 1000),
                new User("Mary Public", 1500),
                new User("Mary Public", 1500)
        ));

        return  "petty-cash";
    }

    @GetMapping("/check")
    public String showCheck(Model model) {

        model.addAttribute("banks", List.of("PNB", "BDO", "BPI"));
        return  "check";
    }

    @GetMapping("/credit-card")
    public String showCreditCard(Model model) {
        return  "credit-card";
    }

    @GetMapping("/user-settings")
    public String showUserSettings(Model model) {

        List<User> users = new ArrayList<>();
        users.add(new User("John Doe", 24));
        users.add(new User("Mary Public", 22));
        users.add(new User("Walter White", 23));

        model.addAttribute("people", users);

        return  "user-settings";
    }
}
