package com.jamersc.springboot.financialhub.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping("list-of-projects")
    public String listOfProjects(Model model) {
        return "project/project";
    }

    @GetMapping("/project-summary")
    public String projectSummary(Model model) {
        return "project/project-summary";
    }
}
