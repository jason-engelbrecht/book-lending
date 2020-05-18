package edu.greenriver.it.booklendingspring.repositories;

import edu.greenriver.it.booklendingspring.models.Lender;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LenderRepository extends CrudRepository<Lender, Long> {
    Optional<Lender> getLenderByUsername(String username);
}
