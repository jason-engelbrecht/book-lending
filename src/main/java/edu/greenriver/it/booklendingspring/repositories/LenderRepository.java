package edu.greenriver.it.booklendingspring.repositories;

import edu.greenriver.it.booklendingspring.models.Lender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Lender repository for CRUD operations
 */
@Repository
public interface LenderRepository extends CrudRepository<Lender, Long> {
    /**
     * Get a lender by username
     * @param username username of lender
     * @return an optional lender
     */
    Optional<Lender> getLenderByUsername(String username);
}
