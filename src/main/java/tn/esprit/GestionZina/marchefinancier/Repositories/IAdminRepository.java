package tn.esprit.GestionZina.marchefinancier.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.GestionZina.marchefinancier.Entites.Admin;

public interface IAdminRepository extends JpaRepository<Admin, Long>  {
}