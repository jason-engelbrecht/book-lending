package edu.greenriver.it.booklendingspring.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
