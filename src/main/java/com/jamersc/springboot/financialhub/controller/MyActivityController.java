package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.dto.ClientAccountDto;
import com.jamersc.springboot.financialhub.dto.LiquidationDto;
import com.jamersc.springboot.financialhub.dto.PettyCashActivityDto;
import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.model.PettyCashActivity;
import com.jamersc.springboot.financialhub.model.User;
import com.jamersc.springboot.financialhub.service.client_accounts.ClientAccountService;
import com.jamersc.springboot.financialhub.service.petty_cash_activity.LiquidationService;
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
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/my-activity")
public class MyActivityController {

    @Autowired
    private UserService userService;
    @Autowired
    private PettyCashActivityService pettyCashActivityService;
    @Autowired
    private LiquidationService liquidationService;
    @Autowired
    private ClientAccountService clientAccountService;

    @GetMapping("/list-of-activities")
    public String listOfActivities(Model model, Principal principal) {

        User loggedInUser = userService.getByUsername(principal.getName());

        List<PettyCashActivityDto> myActivities = pettyCashActivityService.getApprovedPettyCashByReceivedBy(loggedInUser);
        model.addAttribute("myActivities", myActivities);

        return "my-activity/my-activity";
    }

    @GetMapping("/{id}/my-activity")
    public String myActivityEntries(@PathVariable(value = "id") Long id, Model model) {
        PettyCashActivityDto myActivity = pettyCashActivityService.getPettyCashById(id);
        model.addAttribute("myActivity", myActivity);
        List<Liquidation> liquidations = liquidationService.getAllActivityEntriesByPettyCashActivityId(myActivity.getPcActivityId());
        Double totalLiquidationAmount = liquidations.stream().mapToDouble(Liquidation::getCost).sum();
        Double remainingBalance = myActivity.getTotalAmount() - totalLiquidationAmount;
        LiquidationDto newLiquidation = new LiquidationDto();
        List<ClientAccountDto> listOfChargeTo = clientAccountService.getAllClientAccounts(); //
        newLiquidation.setActivityId(myActivity.getPcActivityId());
        model.addAttribute("pettyCash", myActivity); // display petty cash info
        model.addAttribute("liquidations", liquidations); // display liquidations
        model.addAttribute("totalLiquidationAmount", totalLiquidationAmount); // sum total liquidation
        model.addAttribute("remainingBalance", remainingBalance);
        model.addAttribute("listOfChargeTo", listOfChargeTo); //
        model.addAttribute("newLiquidation", newLiquidation); // create new item
        return "my-activity/activity-entry-form";
    }
}
