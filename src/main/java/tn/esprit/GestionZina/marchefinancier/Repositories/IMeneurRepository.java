package tn.esprit.GestionZina.marchefinancier.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.GestionZina.marchefinancier.Entites.Meneur;

public interface IMeneurRepository extends JpaRepository<Meneur, Long> {
}
