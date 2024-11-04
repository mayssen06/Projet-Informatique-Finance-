package tn.esprit.GestionZina.marchefinancier.RestControllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import tn.esprit.GestionZina.marchefinancier.Entites.Role;
import tn.esprit.GestionZina.marchefinancier.Entites.User;
import tn.esprit.GestionZina.marchefinancier.Service.IUserAccountServices;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAccountController {
    private final IUserAccountServices userAccountServices;

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userAccountServices.updateUser(user);
    }

    @GetMapping("/all/{role}")
    public List<User> getAllUsersByRole(@PathVariable Role role) {
        return userAccountServices.getAllUsersByRole(role);
    }

    @GetMapping("/{idUser}")
    public User getUserById(@PathVariable int idUser) {
        return userAccountServices.getUserById(idUser);
    }

    @PutMapping("/updatePassword/{idUser}/{password}")
    public User updatePassword(@PathVariable int idUser, @PathVariable String  password) {
        return userAccountServices.updatePassword(idUser, password);
    }



    @PutMapping("/enable/{idUser}")
    public void enableUser(@PathVariable int idUser) {
        userAccountServices.enableUser(idUser);
    }

    @PutMapping("/disable/{idUser}")
    public void disableUser(@PathVariable int idUser) {
        userAccountServices.disableUser(idUser);
    }
}
