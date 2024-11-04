package tn.esprit.GestionZina.marchefinancier.RestControllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.GestionZina.marchefinancier.Entites.Ordre;
import tn.esprit.GestionZina.marchefinancier.Entites.Titre;
import tn.esprit.GestionZina.marchefinancier.Entites.User;
import tn.esprit.GestionZina.marchefinancier.Repositories.OrdreRepository;
import tn.esprit.GestionZina.marchefinancier.Repositories.TitreRepository;
import tn.esprit.GestionZina.marchefinancier.Service.IOrdre;
import tn.esprit.GestionZina.marchefinancier.Service.ISociete;
import tn.esprit.GestionZina.marchefinancier.Service.IUser;
import tn.esprit.GestionZina.marchefinancier.Service.IUserAccountServices;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class OrdreRestControllers {
    IOrdre iOrdre;
    ISociete iSociete;
    IUser iUser;
    OrdreRepository ordreRepository;
    IUserAccountServices userRepository;
    TitreRepository titreRepository;


    @GetMapping("/afficherOrdre")
    public List<Ordre> afficher() {
        return iOrdre.selectAll();
    }
    @GetMapping("/getordreExecuteparSociete/{id}")
    public List<Ordre> getordreExecuteparSociete(@PathVariable int id) {
        return iOrdre.getordreExecuteparSociete(id);
    }
    @PostMapping("/user/passerOrdre/{id}/{userId}")
    public Ordre ajouterOrdre(@RequestBody Ordre o,@PathVariable int id,@PathVariable int userId){
        User user=userRepository.getUserById(userId);
        return iOrdre.add(o,iSociete.SelectById(id),user);}
    @PostMapping("/user/passerOrdreVente/{id}")
    public Ordre ajouterVente(@RequestBody Ordre o,@PathVariable int id){

        return iOrdre.addVente(o,iSociete.SelectById(id));}

    @GetMapping("afficherOrdreOpposes/{id}")
    public List<Ordre> afficherOrdreOpposé(@PathVariable int id) {
        Ordre o = ordreRepository.findById(id).get();
        String sens = o.getSensOrdre().toString();
        System.out.println(o.getSensOrdre());


        return iOrdre.listOpposeParOrdre(o);
    }


    @GetMapping("CalcilerTitreParUser/{iduser}/{idtitre}")
    public int afficherOrdreOpposé(@PathVariable int iduser, @PathVariable int idtitre) {
        User user = userRepository.getUserById(iduser);
        Titre titre = titreRepository.findTitresByIdTitre(idtitre);

        return iOrdre.NbrTitreParUser(user, titre);
    }

    @GetMapping("afficherprixOrdre/{iduser}/{idtitre}")
    public double afficherprixOrdre(@PathVariable int iduser, @PathVariable int idtitre) {
        User user = userRepository.getUserById(iduser);
        Titre titre = titreRepository.findTitresByIdTitre(idtitre);

        return iOrdre.MontantAchatParTitre(user, titre);
    }
}