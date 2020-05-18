package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.services.BookService;
import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Controller for book routes
 */
@Controller
@RequestMapping("/books")
@ToString
public class BookController {
    private BookService bookService;

    /**
     * Initialize book service
     * @param bookService book service
     */
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Get all books
     * @param model holds view data
     * @return all_books route
     */
    @GetMapping("/all")
    public String allBooks(Model model) {
        Iterable<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/all_books";
    }

    /**
     * Get book by isbn
     * @param isbn isbn of book
     * @param model hold view data
     * @return view_book route
     */
    @GetMapping("/{isbn}")
    public String viewBook(@PathVariable String isbn, Model model) {
        Book book = bookService.getBook(isbn);
        model.addAttribute("book", book);
        return "books/view_book";
    }
}
