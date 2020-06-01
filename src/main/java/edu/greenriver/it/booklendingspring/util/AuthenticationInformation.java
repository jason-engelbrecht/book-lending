package edu.greenriver.it.booklendingspring.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Jason Engelbrecht
 * @version 1.0
 * Holds user authentication data for views
 */
public class AuthenticationInformation {

    /**
     * Checks if a valid user is logged in
     * @return if user is logged in
     */
    @ModelAttribute("validUserLoggedIn")
    public boolean isLoggedIn() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        return (auth != null) &&
               (auth.isAuthenticated()) &&
               !(auth instanceof AnonymousAuthenticationToken);
    }

    /**
     * Gets the logged in users username
     * @return the username
     */
    @ModelAttribute("loggedInUserName")
    public String loggedInUserName() {
        return SecurityContextHolder.getContext()
               .getAuthentication()
               .getName();
    }
}
