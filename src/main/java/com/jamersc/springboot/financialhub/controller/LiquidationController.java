package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.service.cash.LiquidationService;
import com.jamersc.springboot.financialhub.service.cash.PettyCashService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/liquidation")
public class LiquidationController {

    private static final Logger logger = LoggerFactory.getLogger(PettyCashController.class);

    @Autowired
    private PettyCashService pettyCashService;

    @Autowired
    private LiquidationService liquidationService;

    @GetMapping("/liquidation-pcv/{id}")
    public String liquidationForm(@PathVariable(value = "id") Long id, Model model) {
        PettyCash pettyCash = pettyCashService.findPettyCashById(id);
        Liquidation newLiquidation = new Liquidation();
        newLiquidation.setPettyCash(pettyCash);
        model.addAttribute("pettyCash", pettyCash);
        model.addAttribute("liquidations", liquidationService.findByPettyCashVoucherId(pettyCash.getId()));
        model.addAttribute("newLiquidation", newLiquidation);
        return "cash/liquidation-form";
    }

    @PostMapping("/add-item")
    public String addLiquidation(@ModelAttribute("newLiquidation") Liquidation liquidation) {
        if (liquidation.getPettyCash() == null || liquidation.getPettyCash().getId() == null) {
            logger.error("PettyCash association is missing in Liquidation entity.");
            return "redirect:/liquidation/liquidation-pcv/" + liquidation.getPettyCash().getId(); // Or return a specific error view
        }
        // Retrieve the correct PettyCash entity from the database
        PettyCash pettyCash = pettyCashService.findPettyCashById(liquidation.getPettyCash().getId());
        // Associate the retrieved PettyCash with the Liquidation entity
        liquidation.setPettyCash(pettyCash);

        // Save the Liquidation entity
        liquidationService.save(liquidation);

        // Redirect back to the liquidation form for the correct PettyCash voucher
        return "redirect:/liquidation/liquidation-pcv/" + pettyCash.getId();
    }
}
