package tn.esprit.projetspringboot.services;

import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.projetspringboot.entities.AuthenticationResponse;
import tn.esprit.projetspringboot.entities.Meneur;
import tn.esprit.projetspringboot.entities.RefreshTokenRequest;
import tn.esprit.projetspringboot.entities.Trader;

import java.util.HashMap;

public interface IAuthenticationServices {
    Meneur registerMeneur(Meneur meneur);
    Trader registerTrader(Trader trader);
    AuthenticationResponse login(String email, String password);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken);
    HashMap<String,String> forgetPassword(String email);
    HashMap<String,String> resetPassword(String passwordResetToken, String newPassword);
}