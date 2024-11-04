package tn.esprit.GestionZina.marchefinancier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tn.esprit.GestionZina.marchefinancier.Entites.Admin;
import tn.esprit.GestionZina.marchefinancier.Entites.Role;
import tn.esprit.GestionZina.marchefinancier.Entites.User;
import tn.esprit.GestionZina.marchefinancier.Repositories.IAdminRepository;
import tn.esprit.GestionZina.marchefinancier.Repositories.IUserRepository;
@SpringBootApplication
public class GestionZinaApplication {

	private final IUserRepository userRepository;
	private final IAdminRepository adminRepository;

	public GestionZinaApplication(IUserRepository userRepository, IAdminRepository adminRepository) {
		this.userRepository = userRepository;
		this.adminRepository = adminRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(GestionZinaApplication.class, args);
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
