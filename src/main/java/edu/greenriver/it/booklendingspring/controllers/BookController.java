package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.services.BookService;
import edu.greenriver.it.booklendingspring.services.LenderService;
import edu.greenriver.it.booklendingspring.util.AuthenticationInformation;
import lombok.ToString;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Controller for book routes
 */
@Controller
@RequestMapping("/books")
@ToString
public class BookController extends AuthenticationInformation {
    private BookService bookService;
    private LenderService lenderService;

    /**
     * Initialize book & lender service
     * @param bookService book service
     * @param lenderService lender service
     */
    public BookController(BookService bookService, LenderService lenderService) {
        this.bookService = bookService;
        this.lenderService = lenderService;
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
     * Get users books, "my books"
     * @param model holds view data
     * @return my_books
     */
    @GetMapping("/my_books")
    public String myBooks(Model model) {
        Lender lender = lenderService.getLoggedInUser();
        List<Book> books = lender.getBooks();
        model.addAttribute("books", books);
        return "books/my_books";
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

    /**
     * Borrow a book by isbn
     * @param isbn isbn of book
     * @return books owners lender page
     */
    @GetMapping("/borrow/{isbn}")
    public String borrowBook(@PathVariable String isbn) {
        Lender loggedInUser = lenderService.getLoggedInUser();
        Book book = bookService.getBook(isbn);
        lenderService.borrowBook(loggedInUser, book);

        return "redirect:/lenders/" + book.getOwner().getUsername();
    }

    /**
     * Return a book by isbn
     * @param isbn isbn of book
     * @return logged in users lender page
     */
    @GetMapping("/return/{isbn}")
    public String returnBook(@PathVariable String isbn) {
        Lender loggedInUser = lenderService.getLoggedInUser();
        Book book = bookService.getBook(isbn);
        lenderService.returnBook(loggedInUser, book);

        return "redirect:/lenders/" + loggedInUser.getUsername();
    }

    /**
     * Get book cover image by isbn
     * @param isbn isbn of book to get image
     * @param response http response
     * @throws IOException IO exception
     */
    @GetMapping("/image/{isbn}")
    public void bookImage(@PathVariable String isbn, HttpServletResponse response) throws IOException {
        Book book = bookService.getBook(isbn);
        Byte[] bytes = book.getCoverImage();

        byte[] fileBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            fileBytes[i] = bytes[i];
        }

        response.setContentType("image/jpeg");
        InputStream io = new ByteArrayInputStream(fileBytes);
        IOUtils.copy(io, response.getOutputStream());
    }
}
