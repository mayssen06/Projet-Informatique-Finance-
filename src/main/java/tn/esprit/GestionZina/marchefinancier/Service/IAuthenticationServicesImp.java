package tn.esprit.GestionZina.marchefinancier.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.GestionZina.marchefinancier.Entites.*;
import tn.esprit.GestionZina.marchefinancier.config.EmailService;
import tn.esprit.GestionZina.marchefinancier.Entites.Role;
import tn.esprit.GestionZina.marchefinancier.Repositories.ITraderRepository;
import tn.esprit.GestionZina.marchefinancier.Repositories.IMeneurRepository;
import tn.esprit.GestionZina.marchefinancier.Repositories.IUserRepository;

import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class IAuthenticationServicesImp implements IAuthenticationServices {

    private final IUserRepository userRepository;
    private final ITraderRepository traderRepository;
    private final IMeneurRepository meneurRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IJWTServices jwtServices;
    private final EmailService emailService;

    public Meneur registerMeneur(Meneur meneur) {
        meneur.setPassword(passwordEncoder.encode(meneur.getPassword()));
        meneur.setRole(Role.MENEUR);
        return meneurRepository.save(meneur);
    }

    public Trader registerTrader(Trader trader) {
        trader.setPassword(passwordEncoder.encode(trader.getPassword()));
        trader.setRole(Role.TRADER);
        return traderRepository.save(trader);
    }

    public AuthenticationResponse login(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        var jwt = jwtServices.generateToken(user);
        var refreshToken = jwtServices.generateRefreshToken(new HashMap<>(), user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        authenticationResponse.setAccessToken(jwt);
        authenticationResponse.setRefreshToken(refreshToken);

        User userDetails = convertToUserDto(user);
        authenticationResponse.setUserDetails(userDetails);

        return authenticationResponse;
    }

    private User convertToUserDto(User user) {
        User dto = new User();
        dto.setIdUser(user.getIdUser());
        dto.setLasttName(user.getLasttName());
        dto.setFirstName(user.getFirstName());
        // dto.setImage(user.getImage());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken) {
        String userEmail = jwtServices.extractUsername(refreshToken.getRefreshToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));
        if(jwtServices.isTokenValid(refreshToken.getRefreshToken(), user)) {
            var jwt = jwtServices.generateToken(user);

            AuthenticationResponse authenticationResponse = new AuthenticationResponse();

            authenticationResponse.setAccessToken(jwt);
            authenticationResponse.setRefreshToken(refreshToken.getRefreshToken());
            return authenticationResponse;
        }
        return null;
    }

    @Override
    public HashMap<String, String> forgetPassword(String email) {
        HashMap message = new HashMap();

        User userexisting = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        UUID token = UUID.randomUUID();
        userexisting.setPasswordResetToken(token.toString());
        userexisting.setIdUser(userexisting.getIdUser());

        Mail mail = new Mail();

        mail.setSubject("Reset Password");
        mail.setTo(userexisting.getEmail());
        mail.setContent("Votre nouveau TOKEN est : " + "http://localhost:4200/resetpassword/"+userexisting.getPasswordResetToken());
        emailService.sendSimpleEmail(mail);
        userRepository.save(userexisting);
        message.put("user","user FOUND and email is Sent");
        return message;
    }

    @Override
    public HashMap<String,String> resetPassword(@PathVariable String passwordResetToken, String newPassword){
        User userexisting = userRepository.findByPasswordResetToken(passwordResetToken).orElseThrow(() -> new RuntimeException("User not found"));
        HashMap message = new HashMap();
        if (userexisting != null) {
            userexisting.setIdUser(userexisting.getIdUser());
            userexisting.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userexisting.setPasswordResetToken(null);
            userRepository.save(userexisting);
            message.put("resetpassword","succès");
            return message;
        }else
        {
            message.put("resetpassword","Échoué ");
            return message;
        }
    }
}