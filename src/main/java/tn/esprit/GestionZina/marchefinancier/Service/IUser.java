package tn.esprit.GestionZina.marchefinancier.Service;


import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUser {
    UserDetailsService userDetailsService();
     int demandeSolde(int id);
}
