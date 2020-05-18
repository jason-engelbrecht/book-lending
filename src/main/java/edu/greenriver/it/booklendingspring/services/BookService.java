package edu.greenriver.it.booklendingspring.services;

import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(String isbn) {
        return bookRepository.
                getBookByISBN(isbn).
                orElse(null);
    }
}
