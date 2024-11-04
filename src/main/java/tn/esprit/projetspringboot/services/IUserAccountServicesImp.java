package tn.esprit.projetspringboot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetspringboot.entities.Role;
import tn.esprit.projetspringboot.entities.User;
import tn.esprit.projetspringboot.repositories.IUserRepository;

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
    public User getUserById(Long idUser) {
        return userRepository.findById(idUser).orElse(null);
    }

    @Override
    public User updatePassword(Long idUser, String password) {
        User user = userRepository.findById(idUser).orElse(null);
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public User updateImage(Long idUser, MultipartFile file) throws IOException {
        try {
            User user = userRepository.findById(idUser).orElse(null);

            String originalFilename = file.getOriginalFilename();
            String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
            Path fileNameAndPath = Paths.get(uploadDirectory, uniqueFilename);

            if (!Files.exists(fileNameAndPath.getParent())) {
                Files.createDirectories(fileNameAndPath.getParent());
            }

            Files.write(fileNameAndPath, file.getBytes());
            user.setImage(uniqueFilename);
            return userRepository.save(user);
        } catch (IOException e) {
            throw new RuntimeException("Error processing file", e);
        }
    }

    @Override
    public void enableUser(Long idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        user.setAccountStatus(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(Long idUser) {
        User user = userRepository.findById(idUser).orElse(null);
        user.setAccountStatus(false);
        userRepository.save(user);
    }
}
