package tn.esprit.projetspringboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.projetspringboot.entities.Admin;

public interface IAdminRepository extends JpaRepository<Admin, Long>  {
}