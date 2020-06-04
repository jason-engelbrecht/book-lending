package edu.greenriver.it.booklendingspring.services;

import edu.greenriver.it.booklendingspring.models.Authority;
import edu.greenriver.it.booklendingspring.models.Book;
import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.repositories.BookRepository;
import edu.greenriver.it.booklendingspring.repositories.LenderRepository;
import edu.greenriver.it.booklendingspring.util.UserDetailsAdapter;
import lombok.ToString;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Service for implemented lender operations
 */
@Service
@ToString
public class LenderService implements UserDetailsService {
    private LenderRepository lenderRepository;
    private BookRepository bookRepository;

    /**
     * Initialize lender repository
     * @param lenderRepository lender repository
     * @param bookRepository book repository
     */
    public LenderService(LenderRepository lenderRepository, BookRepository bookRepository) {
        this.lenderRepository = lenderRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Get all lenders
     * @return an iterable of all lenders
     */
    public Iterable<Lender> getAllLenders() {
        return lenderRepository.findAll();
    }

    /**
     * Get a lender by username
     * @param username username of lender
     * @return the lender
     */
    public Lender getLender(String username) {
        return lenderRepository.
                getLenderByUsername(username).
                orElse(null);
    }

    public List<Book> getBooksToLoan(Lender lender) {
        return bookRepository.getAllByOwnerAndBorrowerIsNull(lender);
    }

    public List<Book> getLoanedBooks(Lender lender) {
        return bookRepository.getAllByOwnerAndBorrowerIsNotNull(lender);
    }

    public List<Book> getBorrowedBooks(Lender lender) {
        return bookRepository.getAllByBorrower(lender);
    }

    public void borrowBook(Lender lender, Book book) {
        if(book.getBorrower() == null) {
            lender.getBorrowedBooks().add(book);
            book.setBorrower(lender);

            lenderRepository.save(lender);
            bookRepository.save(book);
        }
    }

    /**
     * Register a new lender user
     * @param lender lender to regiser
     * @return lender saved or null
     */
    public Lender registerUser(Lender lender) {
        boolean passwordsMatch = lender.getPassword().equals(lender.getConfirmedPassword());
        if(passwordsMatch) {
            String encodedPassword = new BCryptPasswordEncoder().encode(lender.getPassword());
            lender.setPassword(encodedPassword);

            Authority authority = Authority
                    .builder()
                    .authority("ROLE_USER")
                    .lender(lender)
                    .build();
            lender.getAuthorities().add(authority);

            return lenderRepository.save(lender);
        }
        return null;
    }

    /**
     * Get the currently logged in user
     * @return logged in user or null
     */
    public Lender getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        String username = auth.getName();
        return lenderRepository
               .getLenderByUsername(username)
               .orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Lender> lender = lenderRepository.getLenderByUsername(username);

        if(lender.isPresent()) {
            return new UserDetailsAdapter(lender.get());
        }
        else {
            throw new UsernameNotFoundException("Username or password incorrect");
        }
    }
}
