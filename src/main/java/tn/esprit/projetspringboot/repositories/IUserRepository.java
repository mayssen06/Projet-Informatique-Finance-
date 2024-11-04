package tn.esprit.projetspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetspringboot.entities.Role;
import tn.esprit.projetspringboot.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByPasswordResetToken(String passwordResetToken);
    User findByRole(Role role);

    List<User> findUserByRole(Role role);

}
