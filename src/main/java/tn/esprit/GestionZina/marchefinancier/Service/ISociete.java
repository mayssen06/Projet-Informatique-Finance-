package tn.esprit.GestionZina.marchefinancier.Service;


import tn.esprit.GestionZina.marchefinancier.Entites.Societe;

import java.util.List;

public interface ISociete {
    Societe add(Societe a);
    Societe edit(Societe a);
    List<Societe> selectAll();
    Societe SelectById(int id);
    void deleteById(int id);
    Societe getSocieteByName(String name);

}
