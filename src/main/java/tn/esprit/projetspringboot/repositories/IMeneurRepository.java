package tn.esprit.projetspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetspringboot.entities.Meneur;

public interface IMeneurRepository extends JpaRepository<Meneur, Long> {
}
