package tn.esprit.GestionZina.marchefinancier.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.GestionZina.marchefinancier.Entites.Trader;

public interface ITraderRepository extends JpaRepository<Trader, Long> {
}
