package tn.esprit.GestionZina.marchefinancier.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.GestionZina.marchefinancier.Entites.Ordre;

import java.util.List;

public interface OrdreRepository extends JpaRepository<Ordre,Integer> {
@Query(value = "SELECT v.id_ordre FROM ordre v join ordre a on a.nbre_ordre=v.nbre_ordre where v.sens_ordre='VENTE' and a.titre_id_titre=v.titre_id_titre and a.sens_ordre!= 'ACHAT'",nativeQuery = true)
List<Ordre> selectOrdreVente();

    @Query(value = "SELECT v.id_ordre FROM ordre v join ordre a on a.nbre_ordre=v.nbre_ordre where v.sens_ordre='ACHAT' and a.titre_id_titre=v.titre_id_titre and a.sens_ordre!= 'VENTE'",nativeQuery = true)
    List<Ordre> selectOrdreAchat();

    @Query(value ="select titre.id_titre from titre join societe on titre.societe_id_societe=societe.id_societe where societe.name=?1" ,nativeQuery = true)
    int IdTitreBySociete(String name);
    List<Ordre> getOrdreByUsersEmail(String email);
@Query(value = "SELECT * FROM ordre where  ordre.type_ordre=?1 and ordre.titre_id_titre=?2 and ordre.nbre_ordre=?3 LIMIT 1",nativeQuery = true)
    Ordre OrdreOpposeVente( String Type , int idTitre , int nbrtitre);
    @Query(value = "SELECT * FROM ordre where ordre.sens_ordre!='VENTE' and ordre.type_ordre=?1 and ordre.titre_id_titre=?2 and ordre.nbre_ordre=?3 LIMIT 1",nativeQuery = true)
    Ordre OrdreOpposeAchat( String Type , int idTitre , int nbrtitre);

    @Query(value = "SELECT * FROM ordre where  ordre.type_ordre=?1 and ordre.titre_id_titre=?2 and ordre.status=false ",nativeQuery = true)
    List<Ordre> OrdreOppose( String Type , int idTitre );

    @Query(value = "select ordre.* from ordre where ordre.users_id_user=?1 and ordre.titre_id_titre=?2 and ordre.status=1  ",nativeQuery = true)
    List<Ordre> OrdreUserTitre(int iduser, int idtitre);

    @Query(value ="select count(*) from ordre where ordre.sens_ordre='ACHAT' and ordre.status=1 and ordre.titre_id_titre=?1 " ,nativeQuery = true)
    int DemandeParTitre(int idtitre);

    @Query(value ="select count(*) from ordre where ordre.sens_ordre='VENTE' and ordre.status=1 and ordre.titre_id_titre=?1 " ,nativeQuery = true)
    int OffreParTitre(int idtitre);
    @Query(value = "SELECT * FROM ordre where  ordre.type_ordre='MEILLEURPRIX' and ordre.sens_ordre='VENTE' and ordre.titre_id_titre=?1 and ordre.status=false ORDER BY ordre.prix_passage/ordre.nbre_ordre ASC ",nativeQuery = true)
    List<Ordre> OrdreOpposeMeillerPrixPourAchat( int idTitre );

    @Query(value = "SELECT * FROM ordre where  ordre.type_ordre='MEILLEURPRIX' and ordre.sens_ordre='ACHAT' and ordre.titre_id_titre=?1 and ordre.status=false ORDER BY ordre.prixpassage/ordre.nbre_ordre DESC ",nativeQuery = true)
    List<Ordre> OrdreOpposeMeillerPrixPourVente( int idTitre );
    @Query(value = "SELECT * FROM ordre where ordre.status=true  and titre_id_titre=?1 ",nativeQuery = true)
    List<Ordre> getordreExecuteparSociete( int idTitre );



}
