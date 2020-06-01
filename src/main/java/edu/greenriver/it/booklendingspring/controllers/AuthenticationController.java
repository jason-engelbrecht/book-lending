package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.services.BookService;
import edu.greenriver.it.booklendingspring.services.LenderService;
import edu.greenriver.it.booklendingspring.util.AuthenticationInformation;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 *
 * Authentication controller for all forms
 */
@Controller
@ToString
public class AuthenticationController extends AuthenticationInformation {
    private LenderService lenderService;
    private BookService bookService;

    /**
     * Inject services
     * @param lenderService lender service
     * @param bookService book service
     */
    public AuthenticationController(LenderService lenderService, BookService bookService) {
        this.lenderService = lenderService;
        this.bookService = bookService;
    }

    /**
     * Get lender registration route
     * @param model holds view data
     * @return register
     */
    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("lender", new Lender());
        return "/forms/register";
    }

    /**
     * Post to lender registration route
     * @param lender binded lender object
     * @param model holds view data
     * @return register, or redirect to new lender
     */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute Lender lender, Model model) {
        lender = lenderService.registerUser(lender);
        if(lender == null) {
            model.addAttribute("error", "Passwords do not match!");
            return "/forms/register";
        }
        return "redirect:/login";
    }

    /**
     * User login route
     * @param error invalid login
     * @param logout user logged out
     * @param model holds view data
     * @return login
     */
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        if(error != null) {
            model.addAttribute("message", "Invalid Credentials");
        }
        if(logout != null) {
            model.addAttribute("message", "User Logged Out");
        }
        return "/forms/login";
    }

    /**
     * Access denied route
     * @return access_denied
     */
    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }

    /**
     * Get add book route
     * @param model holds view data
     * @return add_book
     */
    @GetMapping("/add/book")
    public String getAddBook(Model model) {
        model.addAttribute("book", new Book());
        return "/forms/add_book";
    }

    /**
     * Post to add book route
     * @param book binded book object
     * @param model holds view data
     * @param file cover image file
     * @return add_book, or redirect to new book
     */
    @PostMapping("/add/book")
    public String postAddBook(@ModelAttribute Book book, Model model,
                              @RequestParam("cover-image") MultipartFile file) {
        Lender owner = lenderService.getLoggedInUser();
        book = bookService.addBook(book, owner, file);
        if(book == null) {
            model.addAttribute("error", "ISBN is already in use");
            return "/forms/add_book";
        }
        return "redirect:/books/" + book.getIsbn();
    }
}
