package edu.greenriver.it.booklendingspring.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Authority entity
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    @ManyToOne
    private Lender lender;

    @Override
    public String getAuthority() {
        return authority;
    }
}
