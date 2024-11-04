package tn.esprit.marchefinancier.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.marchefinancier.Entites.Portefeuille;

public interface PortefeuilleRepository extends JpaRepository<Portefeuille,Integer> {
    Portefeuille findByUserIdUser(int id);
}
