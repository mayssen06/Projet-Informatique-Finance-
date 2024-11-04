/*package tn.esprit.GestionMortadha.marchefinancier.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    @Query("SELECT  c FROM Transaction c  WHERE c.acheteur.idUser =:id OR c.vendeur.idUser=:id" )
    List<Transaction> getallbyclientid(@Param("id") int id);
    List<Transaction> getTransactionByAcheteurEmail(String email);
    List<Transaction> getTransactionByVendeurEmail(String email);

}*/
