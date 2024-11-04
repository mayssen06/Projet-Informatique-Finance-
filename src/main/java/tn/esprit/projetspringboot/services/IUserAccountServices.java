package tn.esprit.projetspringboot.services;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetspringboot.entities.Role;
import tn.esprit.projetspringboot.entities.User;

import java.io.IOException;
import java.util.List;

public interface IUserAccountServices {

    User updateUser(User user);
    List<User> getAllUsersByRole(Role role);
    User getUserById(Long idUser);

    public User updatePassword (Long idUser, String  password);
    public User updateImage (Long idUser, MultipartFile file) throws IOException;

    void enableUser(Long idUser);
    void disableUser(Long idUser);
}
