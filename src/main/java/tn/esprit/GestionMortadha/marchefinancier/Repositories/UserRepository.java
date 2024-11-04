package tn.esprit.GestionMortadha.marchefinancier.Repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.GestionMortadha.marchefinancier.Entites.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmail(String email);
    User findUserByIdUser(int iduser);
    @Transactional
    @Modifying
    @Query("UPDATE User a " + "SET a.password = ?1    WHERE a.email = ?2")
    void resetPassword(String password,String email);


    Optional<User> findById(int id);
    User findByFirstName(String username);

    @Query("SELECT u FROM User u where u.roles.idrole=1 ORDER BY u.solde DESC ")
    List<User> getClassement();
    @Query("SELECT COUNT(u) FROM User u where u.roles.idrole=1")
    int getNombreUser();
    User findUserByRolesName(String n);
}
