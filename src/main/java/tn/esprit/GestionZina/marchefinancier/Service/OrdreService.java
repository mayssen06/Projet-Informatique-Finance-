package tn.esprit.GestionZina.marchefinancier.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.GestionZina.marchefinancier.Entites.*;
import tn.esprit.GestionZina.marchefinancier.Repositories.OrdreRepository;
import tn.esprit.GestionZina.marchefinancier.Repositories.TitreRepository;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrdreService implements IOrdre {
    private OrdreRepository ordreRepository;
    private TitreRepository titreRepository;
    @Override
    public Ordre add(Ordre a, Societe societe, User user) {

        a.setSensOrdre(SensOrdre.ACHAT);
        a.setDateEmission(new Date());
        a.setPrixTitre(societe.getTitre().getPrix());
        a.setPrixPassage(societe.getTitre().getPrix()*a.getNbreOrdre());
        a.setTitre(titreRepository.findBySocieteIdSociete(societe.getIdSociete()));
        if(user.getSolde()>a.getPrixPassage())
        {
        return ordreRepository.save(a);}
        return null;
    }
    @Override
    public Ordre addVente(Ordre a,Societe societe) {
        a.setSensOrdre(SensOrdre.VENTE);
        a.setDateEmission(new Date());
        a.setPrixTitre(societe.getTitre().getPrix());
        a.setPrixPassage(societe.getTitre().getPrix()*a.getNbreOrdre());
        a.setTitre(titreRepository.findBySocieteIdSociete(societe.getIdSociete()));
        return ordreRepository.save(a);
    }
    @Override
    public void deleteById(int id) {
ordreRepository.deleteById(id);
    }

    @Override
    public List<Ordre> selectAll() {
      return  ordreRepository.findAll();
    }





    @Override
    public List<Ordre> listOpposeParOrdre(Ordre ordre) {
        List<Ordre> list=new ArrayList<>();
        List<Ordre> list1=new ArrayList<>();
        List<Ordre> list2=new ArrayList<>();
        list=ordreRepository.OrdreOppose(ordre.getTypeOrdre().toString(),ordre.getTitre().getIdTitre());
        for(Ordre o:list){

            if((o.getSensOrdre()!=ordre.getSensOrdre()) && (ordre.getUsers().getIdUser()!= o.getUsers().getIdUser())){
                list1.add(o);
            }
        }
        int c=ordre.getNbreOrdre();
        int i=0;
        while ((c>0)&&(i<list1.size())){
            System.out.println(c);
            list2.add(list1.get(i));
            c=c-list2.get(i).getNbreOrdre();
            System.out.println(list2.get(i).getIdOrdre());
            i++;
        }


        return list2;
    }

    @Override
    public int NbrTitreParUser(User user, Titre titre) {
        int nbr=0;
        List<Ordre> list=new ArrayList<>();
        list=ordreRepository.OrdreUserTitre(user.getIdUser(),titre.getIdTitre());
        for(Ordre o:list){
            String sens =o.getSensOrdre().toString();
            if(sens=="ACHAT"){
                nbr=nbr+o.getNbreOrdre();
            }
            else if(sens=="VENTE") {
                nbr=nbr-o.getNbreOrdre();
            }
        }
        return nbr;
    }

    @Override
    public List<Ordre> listOpposeParOrdreMeilleurPrix(Ordre ordre) {
        List<Ordre> list2=new ArrayList<>();
        int c=ordre.getNbreOrdre();
        int i=0;
        if(ordre.getSensOrdre().toString().equals("ACHAT")) {
            List<Ordre> list = ordreRepository.OrdreOpposeMeillerPrixPourAchat(ordre.getTitre().getIdTitre());

            while ((c>0)&&(i<list.size())){
                System.out.println(c);
                list2.add(list.get(i));
                c=c-list2.get(i).getNbreOrdre();
                System.out.println(list2.get(i).getIdOrdre());
                i++;
            }
        }
        if(ordre.getSensOrdre().toString().equals("VENTE")){
            List<Ordre> list = ordreRepository.OrdreOpposeMeillerPrixPourVente(ordre.getTitre().getIdTitre());

            while ((c>0)&&(i<list.size())){
                System.out.println(c);
                list2.add(list.get(i));
                c=c-list2.get(i).getNbreOrdre();
                System.out.println(list2.get(i).getIdOrdre());
                i++;
            }
        }

        return list2;
    }

    @Override
    public List<Ordre> getordreExecuteparSociete(int id) {
        return ordreRepository.getordreExecuteparSociete(id);
    }

    @Override
    public double MontantAchatParTitre(User user, Titre titre) {
        double montant=0;
        List<Ordre> list=new ArrayList<>();
        list=ordreRepository.OrdreUserTitre(user.getIdUser(),titre.getIdTitre());
        List<Ordre> resultatFiltre = list.stream()
                .filter(ordre -> ordre.getSensOrdre().equals(SensOrdre.ACHAT))
                .collect(Collectors.toList());
        for(Ordre o:resultatFiltre){
            String sens =o.getSensOrdre().toString();
            if(sens=="ACHAT"){
                montant=montant+o.getPrixTitre();
            }

        }
        return montant/resultatFiltre.size();    }
}
