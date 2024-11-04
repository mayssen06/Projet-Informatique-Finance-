package tn.esprit.projetspringboot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetspringboot.entities.Role;
import tn.esprit.projetspringboot.entities.User;
import tn.esprit.projetspringboot.services.IUserAccountServices;

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
    public User getUserById(@PathVariable Long idUser) {
        return userAccountServices.getUserById(idUser);
    }

    @PutMapping("/updatePassword/{idUser}/{password}")
    public User updatePassword(@PathVariable Long idUser, @PathVariable String  password) {
        return userAccountServices.updatePassword(idUser, password);
    }

    @PutMapping("/updateImage/{idUser}")
    public User updateImage(@PathVariable Long idUser, @RequestParam("image") MultipartFile file) throws IOException {
        return userAccountServices.updateImage(idUser, file);
    }

    @PutMapping("/enable/{idUser}")
    public void enableUser(@PathVariable Long idUser) {
        userAccountServices.enableUser(idUser);
    }

    @PutMapping("/disable/{idUser}")
    public void disableUser(@PathVariable Long idUser) {
        userAccountServices.disableUser(idUser);
    }
}
