package edu.greenriver.it.booklendingspring.repositories;

import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.models.Lender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Book repository for CRUD operations
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    /**
     * Get a book by isbn
     * @param isbn isbn of book
     * @return an optional book
     */
    Optional<Book> getBookByIsbn(String isbn);

    List<Book> getAllByOwnerAndBorrowerIsNull(Lender lender);

    List<Book> getAllByOwnerAndBorrowerIsNotNull(Lender lender);

    List<Book> getAllByBorrower(Lender lender);
}
