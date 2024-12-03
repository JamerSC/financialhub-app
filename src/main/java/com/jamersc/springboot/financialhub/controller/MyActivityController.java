package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.petty_cash_activity.PettyCashActivityService;
import com.jamersc.springboot.financialhub.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/my-activity")
public class MyActivityController {

    @Autowired
    private UserService userService;
    @Autowired
    private PettyCashActivityService pettyCashActivityService;

    @GetMapping("/list-of-activities")
    public String listOfActivities(Model model, Principal principal) {

        User loggedInUser = userService.getByUsername(principal.getName());

        List<PettyCashActivity> myActivities = pettyCashActivityService.getApprovedPettyCashByReceivedBy(loggedInUser);
        model.addAttribute("myActivities", myActivities);

        return "my-activity/my-activity";
    }

    @GetMapping("/{id}/my-activity")
    public String myActivityEntries(@PathVariable(value = "id") Long id, Model model) {
        PettyCashActivityDto myActivity = pettyCashActivityService.getPettyCashById(id);
        model.addAttribute("myActivity", myActivity);
        /*model.addAttribute("entries", "Entries of My Activity");
        model.addAttribute("myActivity", new PettyCashActivityDto());*/
        return "my-activity/activity-entry-form";
    }
}
