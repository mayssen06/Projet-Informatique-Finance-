package tn.esprit.GestionZina.marchefinancier.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.GestionZina.marchefinancier.Entites.Role;
import tn.esprit.GestionZina.marchefinancier.Service.IUserAccountServices;
import tn.esprit.GestionZina.marchefinancier.Entites.User;
import tn.esprit.GestionZina.marchefinancier.Repositories.IUserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class IUserAccountServicesImp implements IUserAccountServices {
    public static String uploadDirectory = System.getProperty("user.dir") + "/uploadUser";
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsersByRole(Role role) {
        return userRepository.findUserByRole(role);
    }

    @Override
    public User getUserById(int idUser) {
        return userRepository.findById(idUser).orElse(null);
    }

    @Override
    public User updatePassword(int idUser, String password) {
        User user = userRepository.findById(idUser).orElse(null);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }


    @Override
    public void enableUser(int idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        user.setAccountStatus(true);
        userRepository.save(user);
    }


    public void disableUser(int idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        user.setAccountStatus(false);
        userRepository.save(user);
    }
}
