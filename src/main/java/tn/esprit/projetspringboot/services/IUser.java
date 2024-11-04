package tn.esprit.projetspringboot.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUser {
    UserDetailsService userDetailsService();
}
