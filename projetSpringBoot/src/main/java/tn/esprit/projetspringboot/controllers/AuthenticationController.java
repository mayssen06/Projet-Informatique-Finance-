package tn.esprit.projetspringboot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.projetspringboot.entities.*;
import tn.esprit.projetspringboot.services.IAuthenticationServices;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    public static String uploadDirectory = System.getProperty("user.dir") + "/uploadUser";

    private final IAuthenticationServices authenticationServices;

    @PostMapping("/registerMeneur")
    public ResponseEntity<Meneur> registerMeneur(@RequestParam("nom") String nom,
                                                     @RequestParam("prenom") String prenom,
                                                     @RequestParam("email") String email,
                                                     @RequestParam("password") String password,
                                                     @RequestParam("image") MultipartFile file) throws IOException {
        Meneur meneur = new Meneur();
        meneur.setNom(nom);
        meneur.setPrenom(prenom);
        meneur.setEmail(email);
        meneur.setPassword(password);
        meneur.setAccountStatus(true);

        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        Path fileNameAndPath = Paths.get(uploadDirectory, uniqueFilename);

        if (!Files.exists(fileNameAndPath.getParent())) {
            Files.createDirectories(fileNameAndPath.getParent());
        }

        Files.write(fileNameAndPath, file.getBytes());
        meneur.setImage(uniqueFilename);

        Meneur savedMeneur = authenticationServices.registerMeneur(meneur);
        return ResponseEntity.ok(savedMeneur);
    }

    @PostMapping("/registerTrader")
    public ResponseEntity<Trader> registerTrader(@RequestParam("nom") String nom,
                                                 @RequestParam("prenom") String prenom,
                                                 @RequestParam("email") String email,
                                                 @RequestParam("password") String password,
                                                 @RequestParam("image") MultipartFile file) throws IOException {
        Trader trader = new Trader();
        trader.setNom(nom);
        trader.setPrenom(prenom);
        trader.setEmail(email);
        trader.setPassword(password);
        trader.setAccountStatus(true);

        String originalFilename = file.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
        Path fileNameAndPath = Paths.get(uploadDirectory, uniqueFilename);

        if (!Files.exists(fileNameAndPath.getParent())) {
            Files.createDirectories(fileNameAndPath.getParent());
        }

        Files.write(fileNameAndPath, file.getBytes());
        trader.setImage(uniqueFilename);

        Trader savedTrader = authenticationServices.registerTrader(trader);
        return ResponseEntity.ok(savedTrader);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {

        Path filePath = Paths.get(uploadDirectory).resolve(filename);
        Resource file = new UrlResource(filePath.toUri());

        if (file.exists() || file.isReadable()) {
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody User user) {
        return authenticationServices.login(user.getEmail(), user.getPassword());
    }

    @PostMapping("/refreshToken")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest refreshToken) {
        return authenticationServices.refreshToken(refreshToken);
    }

    @PostMapping("/forgetpassword")
    public HashMap<String,String> forgetPassword(@RequestParam String email){
        return authenticationServices.forgetPassword(email);
    }

    @PostMapping("/resetPassword/{passwordResetToken}")
    public HashMap<String,String> resetPassword(@PathVariable String passwordResetToken, String newPassword){
        return authenticationServices.resetPassword(passwordResetToken, newPassword);
    }
}
