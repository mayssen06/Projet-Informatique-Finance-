package tn.esprit.marchefinancier.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.marchefinancier.Entites.Societe;

public interface SocieteRepository extends JpaRepository<Societe,Integer> {
    Societe findByName(String s);
    Societe findByTitreIdTitre(int id);
}
