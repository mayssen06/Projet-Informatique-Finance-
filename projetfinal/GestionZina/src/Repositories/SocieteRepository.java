package tn.esprit.GestionZina.marchefinancier.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.GestionZina.marchefinancier.Entites.Societe;

public interface SocieteRepository extends JpaRepository<Societe,Integer> {
Societe findByName(String s);
Societe findByTitreIdTitre(int id);
}
