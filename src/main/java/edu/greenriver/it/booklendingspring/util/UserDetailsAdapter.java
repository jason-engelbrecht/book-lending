package edu.greenriver.it.booklendingspring.util;

import edu.greenriver.it.booklendingspring.models.Lender;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Adapts UserDetails to work with lenders
 */
@ToString
public class UserDetailsAdapter implements UserDetails {
    private Lender lender;

    /**
     * Initialize lender
     * @param lender lender
     */
    public UserDetailsAdapter(Lender lender) {
        this.lender = lender;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return lender.getAuthorities();
    }

    @Override
    public String getPassword() {
        return lender.getPassword();
    }

    @Override
    public String getUsername() {
        return lender.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
