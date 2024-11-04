package tn.esprit.GestionZina.marchefinancier.Service;

import tn.esprit.GestionZina.marchefinancier.Entites.AuthenticationResponse;
import tn.esprit.GestionZina.marchefinancier.Entites.Meneur;
import tn.esprit.GestionZina.marchefinancier.Entites.RefreshTokenRequest;
import tn.esprit.GestionZina.marchefinancier.Entites.Trader;

import java.util.HashMap;

public interface IAuthenticationServices {
    Meneur registerMeneur(Meneur meneur);
    Trader registerTrader(Trader trader);
    AuthenticationResponse login(String email, String password);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken);
    HashMap<String,String> forgetPassword(String email);
    HashMap<String,String> resetPassword(String passwordResetToken, String newPassword);
}