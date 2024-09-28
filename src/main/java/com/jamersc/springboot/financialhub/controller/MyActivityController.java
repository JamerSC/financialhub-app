package com.jamersc.springboot.financialhub.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/my-activity")
public class MyActivityController {

    @GetMapping("/list-of-activities")
    public String listOfActivities(Model model) {
        return "my-activity/my-activity";
    }
}
