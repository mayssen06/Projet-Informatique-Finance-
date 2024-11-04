package tn.esprit.projetspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetspringboot.entities.Trader;

public interface ITraderRepository extends JpaRepository<Trader, Long> {
}
