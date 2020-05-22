package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.services.LenderService;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@ToString
public class AuthenticationController {
    private LenderService lenderService;

    public AuthenticationController(LenderService lenderService) {
        this.lenderService = lenderService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("lender", new Lender());
        return "/forms/register";
    }
}
