package tn.esprit.GestionMortadha.marchefinancier.Service;


import tn.esprit.GestionMortadha.marchefinancier.Entites.Societe;

import java.util.List;

public interface ISociete {
    Societe add(Societe a);
    Societe edit(Societe a);
    List<Societe> selectAll();
    Societe SelectById(int id);
    void deleteById(int id);
    Societe getSocieteByName(String name);

}
