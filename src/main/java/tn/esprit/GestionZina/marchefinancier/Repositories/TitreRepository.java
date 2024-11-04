package tn.esprit.GestionZina.marchefinancier.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.GestionZina.marchefinancier.Entites.Titre;

import java.util.List;

public interface TitreRepository extends JpaRepository<Titre,Integer> {
    Titre findBySocieteIdSociete(int id);
    @Query(value ="SELECT t.* FROM Portefeuille p JOIN portefeuille_titre_listp pt ON p.id_portefeuille = pt.portefeuille_id_portefeuille JOIN Titre t ON pt.titre_listp_id_titre = t.id_titre WHERE p.id_portefeuille=?1" ,nativeQuery = true)
    List<Titre> gettitrebyportefeuille(int i);
    Titre findTitresByIdTitre(int idtitre);


}
