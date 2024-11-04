package tn.esprit.projetspringboot.services;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserServices {
    UserDetailsService userDetailsService();
}
