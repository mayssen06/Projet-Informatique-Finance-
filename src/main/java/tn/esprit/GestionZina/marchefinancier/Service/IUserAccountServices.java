package tn.esprit.GestionZina.marchefinancier.Service;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.GestionZina.marchefinancier.Entites.Role;
import tn.esprit.GestionZina.marchefinancier.Entites.User;

import java.io.IOException;
import java.util.List;

public interface IUserAccountServices {

    User updateUser(User user);
    List<User> getAllUsersByRole(Role role);
    User getUserById(int idUser);

    User updatePassword(int idUser, String password);

    void enableUser(int idUser);
    void disableUser(int idUser);
}
