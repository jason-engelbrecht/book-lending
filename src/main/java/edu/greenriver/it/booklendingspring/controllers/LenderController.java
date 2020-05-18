package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.services.LenderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lenders")
public class LenderController {
    private LenderService lenderService;

    public LenderController(LenderService lenderService) {
        this.lenderService = lenderService;
    }

    @GetMapping("/all")
    public String allLenders(Model model) {
        Iterable<Lender> lenders = lenderService.getAllLenders();
        model.addAttribute("lenders", lenders);
        return "lenders/all_lenders";
    }

    @GetMapping("/{username}")
    public String viewLender(@PathVariable String username, Model model) {
        Lender lender = lenderService.getLender(username);
        model.addAttribute("lender", lender);
        return "lenders/view_lender";
    }
}
