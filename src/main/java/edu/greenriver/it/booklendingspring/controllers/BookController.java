package edu.greenriver.it.booklendingspring.controllers;

import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.services.BookService;
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
