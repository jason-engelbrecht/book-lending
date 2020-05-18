package edu.greenriver.it.booklendingspring.services;

import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.repositories.LenderRepository;
import org.springframework.stereotype.Service;

@Service
public class LenderService {
    private LenderRepository lenderRepository;

    public LenderService(LenderRepository lenderRepository) {
        this.lenderRepository = lenderRepository;
    }

    public Iterable<Lender> getAllLenders() {
        return lenderRepository.findAll();
    }
}
