package edu.greenriver.it.booklendingspring.services;

import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.repositories.LenderRepository;
import lombok.ToString;
import org.springframework.stereotype.Service;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Service for implemented lender operations
 */
@Service
@ToString
public class LenderService {
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
}
