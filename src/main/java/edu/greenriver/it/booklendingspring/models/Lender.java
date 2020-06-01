package edu.greenriver.it.booklendingspring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Lender entity
 */
@Entity //persist objects - creates lenders table
@Data //getters, setters, toString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    private Long id;
    private String username;
    private String password;

    @Transient //don't persist
    private String confirmedPassword;

    private String firstName;
    private String lastName;

    @Lob //large text
    private String bio;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "lender")
    private Collection<Authority> authorities = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Book> books = new ArrayList<>();
}