package edu.greenriver.it.booklendingspring.services;

import edu.greenriver.it.booklendingspring.models.Authority;
import edu.greenriver.it.booklendingspring.models.Lender;
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

    /**
     * Initialize lender repository
     * @param lenderRepository lender repository
     */
    public LenderService(LenderRepository lenderRepository) {
        this.lenderRepository = lenderRepository;
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
