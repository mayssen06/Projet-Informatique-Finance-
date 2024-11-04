package tn.esprit.GestionZina.marchefinancier.Service;

import tn.esprit.GestionZina.marchefinancier.Entites.Ordre;
import tn.esprit.GestionZina.marchefinancier.Entites.Societe;
import tn.esprit.GestionZina.marchefinancier.Entites.Titre;
import tn.esprit.GestionZina.marchefinancier.Entites.User;

import java.util.List;

public interface IOrdre {
    Ordre add(Ordre a, Societe societe, User user);
    void deleteById(int id);
    List<Ordre> selectAll();
    List<Ordre> listOpposeParOrdre(Ordre ordre);
    int NbrTitreParUser(User user, Titre titre);
    Ordre addVente(Ordre a,Societe societe);
    List<Ordre> listOpposeParOrdreMeilleurPrix(Ordre ordre);
    List<Ordre> getordreExecuteparSociete(int id);
    double MontantAchatParTitre(User user, Titre titre);


}
