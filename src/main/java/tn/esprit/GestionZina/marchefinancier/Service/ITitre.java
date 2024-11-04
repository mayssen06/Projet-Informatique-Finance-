package tn.esprit.GestionZina.marchefinancier.Service;

import tn.esprit.GestionZina.marchefinancier.Entites.Titre;

import java.io.IOException;
import java.util.List;

public interface ITitre {

    Titre add(Titre a);
    Titre edit(Titre a);
    List<Titre> selectAll();
    Titre SelectById(int id);
    void deleteById(int id);


    List<Titre> gettitrebyPortefeuille(int id);
    Titre findbyidSociete(int id);
    double VariationAction(int idtitre) ;
    double RisqueMarkowvitz(int idtitre) throws IOException;
    double calculateHistoricalVolatility(Titre titre) throws IOException;
}
