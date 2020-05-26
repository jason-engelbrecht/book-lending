package edu.greenriver.it.booklendingspring.services;

import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.repositories.BookRepository;
import lombok.ToString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Service for implemented book operations
 */
@Service
@ToString
public class BookService {
    private BookRepository bookRepository;

    /**
     * Initilize book repository
     * @param bookRepository book repository
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Get all books
     * @return an iterable of all books
     */
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Get a book by isbn
     * @param isbn isbn of book
     * @return the book
     */
    public Book getBook(String isbn) {
        return bookRepository.
                getBookByIsbn(isbn).
                orElse(null);
    }

    public Book addBook(Book book, MultipartFile file) {
        boolean uniqueIsbn = bookRepository.getBookByIsbn(book.getIsbn()).isEmpty();
        if(uniqueIsbn) {
            saveImageToBook(book, file);
            return bookRepository.save(book);
        }
        return null;
    }

    private void saveImageToBook(Book book, MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            Byte[] bytes = new Byte[fileBytes.length];
            for(int i = 0; i < fileBytes.length; i++) {
                bytes[i] = fileBytes[i];
            }
            book.setCoverImage(bytes);
        }
        catch (IOException e) {
            System.out.println("Add cover image error: " + e.getMessage());
        }
    }
}
