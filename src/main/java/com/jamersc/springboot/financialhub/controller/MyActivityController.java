package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.pettycash.PettyCashService;
import com.jamersc.springboot.financialhub.service.user.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/my-activity")
public class MyActivityController {

    private static final Logger logger = LoggerFactory.getLogger(MyActivityController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PettyCashService pettyCashService;

    @GetMapping("/list-of-activities")
    public String listOfActivities(Model model, Principal principal) {

        User loggedInUser = userService.getByUsername(principal.getName());

        List<PettyCash> myActivities = pettyCashService.getApprovedPettyCashByReceivedBy(loggedInUser);
        model.addAttribute("myActivities", myActivities);

        return "my-activity/my-activity";
    }

    @GetMapping("/{id}/my-activity-entries")
    public String myActivityEntries(Model model) {
        //
        model.addAttribute("entries", "Entries of My Activity");
        return "my-activity/activity-entries";
    }
}
