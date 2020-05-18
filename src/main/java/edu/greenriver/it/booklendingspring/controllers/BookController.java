package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.services.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public String allBooks(Model model) {
        Iterable<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/all_books";
    }

    @GetMapping("/{isbn}")
    public String viewBook(@PathVariable String isbn, Model model) {
        Book book = bookService.getBook(isbn);
        model.addAttribute("book", book);
        return "books/view_book";
    }
}
