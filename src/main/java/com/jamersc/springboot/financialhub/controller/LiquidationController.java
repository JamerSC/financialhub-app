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
        Double totalAmount = liquidations.stream().mapToDouble(Liquidation::getAmount).sum();
        Liquidation newLiquidation = new Liquidation();
        newLiquidation.setPettyCash(pettyCash);
        model.addAttribute("pettyCash", pettyCash);
        model.addAttribute("liquidations", liquidations);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("newLiquidation", newLiquidation);
        return "cash/liquidation-form";
    }

    @PostMapping("/add-item")
    public String addLiquidation(@ModelAttribute("newLiquidation") Liquidation liquidation) {
        liquidationService.save(liquidation);
        return "redirect:/liquidation/liquidation-pcv/" + liquidation.getPettyCash().getId();
    }
}
