package tn.esprit.GestionZina.marchefinancier.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.GestionMortadha.marchefinancier.Entites.Portefeuille;

import java.util.List;

public interface PortefeuilleRepository extends JpaRepository<Portefeuille,Integer> {

    Portefeuille findByUserIdUser(int id);

}
