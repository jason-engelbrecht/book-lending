package edu.greenriver.it.booklendingspring.util;

import edu.greenriver.it.booklendingspring.models.Authority;
import edu.greenriver.it.booklendingspring.models.Lender;
import edu.greenriver.it.booklendingspring.repositories.LenderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class AccountSetup implements CommandLineRunner {
    private LenderRepository lenderRepository;

    public AccountSetup(LenderRepository lenderRepository) {
        this.lenderRepository = lenderRepository;
    }

    @Override
    public void run(String... args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Lender admin = Lender.builder()
                .username("admin")
                .password(encoder.encode("password"))
                .authorities(new HashSet<>())
                .build();

        admin.getAuthorities().addAll(Arrays.asList(
                Authority.builder()
                    .authority("ROLE_ADMIN")
                    .lender(admin)
                    .build(),
                Authority.builder()
                    .authority("ROLE_USER")
                    .lender(admin)
                    .build()
        ));
        lenderRepository.save(admin);
    }
}
