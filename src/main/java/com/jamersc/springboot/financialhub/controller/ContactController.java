package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Sample;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@AllArgsConstructor
@Controller
@RequestMapping("/contacts")
public class ContactController {

    @GetMapping("/contact-list")
    public String payeeList(Model model) {
        model.addAttribute("contacts", Arrays.asList(
                new Sample("John", "Doe", 25),
                new Sample("Mary", "Public", 26),
                new Sample("Susan", "Swan", 30)
        ));
        return "contact/contact";
    }
}
