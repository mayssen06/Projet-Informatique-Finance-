package tn.esprit.projetspringboot;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tn.esprit.projetspringboot.entities.Admin;
import tn.esprit.projetspringboot.entities.Role;
import tn.esprit.projetspringboot.entities.User;
import tn.esprit.projetspringboot.repositories.IAdminRepository;
import tn.esprit.projetspringboot.repositories.IUserRepository;

@RequiredArgsConstructor
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
public class ProjetSpringBootApplication implements CommandLineRunner {

	private final IUserRepository userRepository;
	private final IAdminRepository adminRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProjetSpringBootApplication.class, args);
	}

	public void run(String... args) {
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if (adminAccount == null) {
			Admin admin = new Admin();
			admin.setEmail("admin@smfinancier.com");
			admin.setLasttName("admin");
			admin.setFirstName("admin");
			admin.setRole(Role.ADMIN);
			admin.setAccountStatus(true);
			admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
			//admin.setImage("admin.png");
			adminRepository.save(admin);
		}
	}

}
