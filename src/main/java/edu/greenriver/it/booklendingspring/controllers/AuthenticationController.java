package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.services.BookService;
import edu.greenriver.it.booklendingspring.services.LenderService;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@ToString
public class AuthenticationController {
    private LenderService lenderService;
    private BookService bookService;

    public AuthenticationController(LenderService lenderService, BookService bookService) {
        this.lenderService = lenderService;
        this.bookService = bookService;
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("lender", new Lender());
        return "/forms/register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute Lender lender, Model model) {
        lender = lenderService.registerUser(lender);
        if(lender == null) {
            model.addAttribute("error", "Passwords do not match!");
            return "/forms/register";
        }
        return "redirect:/lenders/" + lender.getUsername();
    }

    @GetMapping("/add/book")
    public String getAddBook(Model model) {
        model.addAttribute("book", new Book());
        return "/forms/add_book";
    }
}
