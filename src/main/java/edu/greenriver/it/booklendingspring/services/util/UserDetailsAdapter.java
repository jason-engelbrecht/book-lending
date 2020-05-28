package edu.greenriver.it.booklendingspring.services.util;

import edu.greenriver.it.booklendingspring.models.Lender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsAdapter implements UserDetails {
    private Lender lender;

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
