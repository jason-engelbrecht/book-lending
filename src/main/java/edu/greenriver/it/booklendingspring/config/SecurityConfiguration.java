package edu.greenriver.it.booklendingspring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private UserDetailsService service;

    public SecurityConfiguration(@Qualifier("lenderService") UserDetailsService service) {
        this.service = service;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = passwordEncoder();
        auth.userDetailsService(service)
            .passwordEncoder(encoder);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/js/**").and()
           .ignoring().antMatchers("/css/**").and()
           .ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/books/all").hasAuthority("ROLE_ADMIN")
                .antMatchers("/lenders/**").hasAuthority("ROLE_USER")
                .antMatchers("/books/**").hasAuthority("ROLE_USER")
                .antMatchers("/add/book").hasAuthority("ROLE_USER")
                .antMatchers("/**").permitAll()
            .and().formLogin()
                .permitAll()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
            .and().exceptionHandling()
                .accessDeniedPage("/access_denied")
            .and().logout()
            .permitAll()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout=true");
    }
}
