package edu.greenriver.it.booklendingspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jason Engelbrecht
 * @version 0.1
 * Entry Point of spring app
 */
@SpringBootApplication
public class BookLendingSpringApplication {

    /**
     * Run book lending app
     * @param args cmd
     */
    public static void main(String[] args) {
        SpringApplication.run(BookLendingSpringApplication.class, args);
    }

}
