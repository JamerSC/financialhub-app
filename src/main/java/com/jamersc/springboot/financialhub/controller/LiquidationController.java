package com.jamersc.springboot.financialhub.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/liquidation")
public class LiquidationController {

   /* private static final Logger logger = LoggerFactory.getLogger(LiquidationController.class);

    @Autowired
    private PettyCashService pettyCashService;

    @Autowired
    private LiquidationService liquidationService;

    @Autowired
    private ContactService contactService;*/

   /* @GetMapping("/liquidation-pcv/{id}")
    public String liquidationForm(@PathVariable(value = "id") Long id, Model model) {
        PettyCash pettyCash = pettyCashService.findPettyCashById(id);
        List<Liquidation> liquidations = liquidationService.findByPettyCashVoucherId(pettyCash.getPettyCashId());
        Double totalLiquidationAmount = liquidations.stream().mapToDouble(Liquidation::getCost).sum();
        Double remainingBalance = pettyCash.getTotalAmount() - totalLiquidationAmount;
        Liquidation newLiquidation = new Liquidation();
        List<Contact> listOfChargeTo = contactService.getAllContacts(); //
        newLiquidation.setPettyCash(pettyCash);
        model.addAttribute("pettyCash", pettyCash); // display petty cash info
        model.addAttribute("liquidations", liquidations); // display liquidations
        model.addAttribute("totalLiquidationAmount", totalLiquidationAmount); // sum total liquidation
        model.addAttribute("remainingBalance", remainingBalance);
        model.addAttribute("listOfChargeTo", listOfChargeTo); //
        model.addAttribute("newLiquidation", newLiquidation); // create new item
        return "petty-cash/liquidation-form";
    }*/

   /* @PostMapping("/add-item")
    public String addLiquidation(@ModelAttribute("newLiquidation") Liquidation liquidation) {
        liquidationService.save(liquidation);
        return "redirect:/liquidation/liquidation-pcv/" + liquidation.getPettyCash().getPettyCashId();
    }

    @GetMapping("/delete-item/{id}")
    public String deleteItem(@PathVariable(value = "id") Long id) {
        Liquidation liquidation = liquidationService.findLiquidationById(id);
        liquidationService.deleteLiquidationItemById(liquidation.getId());
        return "redirect:/liquidation/liquidation-pcv/" + liquidation.getPettyCash().getPettyCashId();
    }*/
}
