package tn.esprit.GestionZina.marchefinancier.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.GestionZina.marchefinancier.Entites.Role;
import tn.esprit.GestionZina.marchefinancier.Entites.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPasswordResetToken(String passwordResetToken);
    User findByRole(Role role);

    List<User> findUserByRole(Role role);
    User findUserByRolesName(String n);

}
