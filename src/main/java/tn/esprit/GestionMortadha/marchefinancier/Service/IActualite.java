package tn.esprit.GestionMortadha.marchefinancier.Service;



import tn.esprit.GestionMortadha.marchefinancier.Entites.Actualite;

import java.util.List;

public interface IActualite {
    Actualite add(Actualite a);
    Actualite edit(Actualite a);
    List<Actualite> selectAll();
    Actualite SelectById(int id);
    void deleteById(int id);
}
