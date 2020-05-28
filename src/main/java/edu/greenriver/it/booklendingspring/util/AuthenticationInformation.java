package edu.greenriver.it.booklendingspring.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

public class AuthenticationInformation {

    @ModelAttribute("validUserLoggedIn")
    public boolean isLoggedIn() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        return (auth != null) &&
               (auth.isAuthenticated()) &&
               !(auth instanceof AnonymousAuthenticationToken);
    }

    @ModelAttribute("loggedInUserName")
    public String loggedInUserName() {
        return SecurityContextHolder.getContext()
               .getAuthentication()
               .getName();
    }
}
