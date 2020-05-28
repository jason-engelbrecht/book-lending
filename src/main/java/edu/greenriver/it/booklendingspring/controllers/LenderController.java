package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.services.LenderService;
import edu.greenriver.it.booklendingspring.util.AuthenticationInformation;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Controller for lender routes
 */
@Controller
@RequestMapping("/lenders")
@ToString
public class LenderController extends AuthenticationInformation {
    private LenderService lenderService;

    /**
     * Initilize lender service
     * @param lenderService lender service
     */
    public LenderController(LenderService lenderService) {
        this.lenderService = lenderService;
    }

    /**
     * Get all lenders
     * @param model holds view data
     * @return all_lenders page
     */
    @GetMapping("/all")
    public String allLenders(Model model) {
        Iterable<Lender> lenders = lenderService.getAllLenders();
        model.addAttribute("lenders", lenders);
        return "lenders/all_lenders";
    }

    /**
     * Get a lender by their username
     * @param username username of lender
     * @param model holds view data
     * @return view_lender page
     */
    @GetMapping("/{username}")
    public String viewLender(@PathVariable String username, Model model) {
        Lender lender = lenderService.getLender(username);
        model.addAttribute("lender", lender);
        return "lenders/view_lender";
    }
}
