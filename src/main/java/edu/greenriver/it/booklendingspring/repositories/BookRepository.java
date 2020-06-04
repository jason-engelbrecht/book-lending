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

    /**
     * Get all by owner, borrower null
     * @param lender lender
     * @return list of books
     */
    List<Book> getAllByOwnerAndBorrowerIsNull(Lender lender);

    /**
     * Get all by owner with borrower
     * @param lender lender
     * @return list of books
     */
    List<Book> getAllByOwnerAndBorrowerIsNotNull(Lender lender);

    /**
     * Get all by borrower
     * @param lender lender
     * @return list of books
     */
    List<Book> getAllByBorrower(Lender lender);
}
