package com.jamersc.springboot.financialhub.controller;

import com.jamersc.springboot.financialhub.model.Liquidation;
import com.jamersc.springboot.financialhub.model.PettyCash;
import com.jamersc.springboot.financialhub.service.pettycash.LiquidationService;
import com.jamersc.springboot.financialhub.service.pettycash.PettyCashService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<Liquidation> liquidations = liquidationService.findByPettyCashVoucherId(pettyCash.getId());
        Double totalLiquidationAmount = liquidations.stream().mapToDouble(Liquidation::getAmount).sum();
        Double remainingBalance = pettyCash.getTotalAmount() - totalLiquidationAmount;
        Liquidation newLiquidation = new Liquidation();
        newLiquidation.setPettyCash(pettyCash);
        model.addAttribute("pettyCash", pettyCash); // display petty cash info
        model.addAttribute("liquidations", liquidations); // display liquidations
        model.addAttribute("totalLiquidationAmount", totalLiquidationAmount); // sum total liquidation
        model.addAttribute("remainingBalance", remainingBalance);
        model.addAttribute("newLiquidation", newLiquidation); // create new item
        return "petty-cash/liquidation-form";
    }

    @PostMapping("/add-item")
    public String addLiquidation(@ModelAttribute("newLiquidation") Liquidation liquidation) {
        liquidationService.save(liquidation);
        return "redirect:/liquidation/liquidation-pcv/" + liquidation.getPettyCash().getId();
    }

    @GetMapping("/delete-item/{id}")
    public String deleteItem(@PathVariable(value = "id") Long id) {
        Liquidation liquidation = liquidationService.findLiquidationById(id);
        liquidationService.deleteLiquidationItemById(liquidation.getId());
        return "redirect:/liquidation/liquidation-pcv/" + liquidation.getPettyCash().getId();
    }
}
