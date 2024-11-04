package tn.esprit.GestionZina.marchefinancier.Service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.GestionZina.marchefinancier.Entites.Ordre;
import tn.esprit.GestionZina.marchefinancier.Entites.Transaction;
import tn.esprit.GestionZina.marchefinancier.Entites.TypeOrdre;
import tn.esprit.GestionZina.marchefinancier.Entites.User;
import tn.esprit.GestionZina.marchefinancier.Repositories.OrdreRepository;
import tn.esprit.GestionZina.marchefinancier.Repositories.TransactionRepository;
import tn.esprit.GestionZina.marchefinancier.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class TransactionService  implements ITransaction {
    private TransactionRepository transactionRepository;
    private OrdreRepository ordreRepository;
    private IOrdre iOrdre;
    private UserRepository userRepository;

    @Override
    @Transactional
    public void addTransaction(Ordre ordre) {
        List<Ordre> ListeOrd =new ArrayList<>();
        User A ;
        User V;
        double c, prix = 0;
        double prixTitre=ordre.getPrixPassage()/ordre.getNbreOrdre();
        System.out.println("prixTitre");
        System.out.println(prixTitre);
        int i ;
        if(ordre.getTypeOrdre().equals(TypeOrdre.MEILLEURPRIX))
        {
            ListeOrd = iOrdre.listOpposeParOrdreMeilleurPrix(ordre);
        }
        else if (ordre.getTypeOrdre()== TypeOrdre.AUMARCHE)
        {
            ListeOrd = iOrdre.listOpposeParOrdre(ordre);
        }
        c = ordre.getNbreOrdre();
        String type = String.valueOf(ordre.getSensOrdre());
        if (type.equals("VENTE") && !ordre.isStatus()) {
            for (i = 0; i < ListeOrd.size(); i++) {
                Transaction transaction = new Transaction();
                if (c >= ListeOrd.get(i).getNbreOrdre()) {
                    prix = ListeOrd.get(i).getNbreOrdre() * prixTitre;
                    V = ordre.getUsers();
                    A = ListeOrd.get(i).getUsers();
                    A.getPortefeuille().getTitreListP().add(ListeOrd.get(i).getTitre());
                    A.setSolde(A.getSolde()-prix);
                    if (V.getDemandeSolde()<=0)
                    {   V.setSolde(V.getSolde()-V.getDemandeSolde());
                        V.setDemandeSolde(0);
                        V.setSolde(V.getSolde()+prix);
                    }
                    else
                    {   V.setSolde(V.getSolde()-prix);
                        V.setDemandeSolde(V.getDemandeSolde()-prix);
                        User Admin=userRepository.findUserByRolesName("[ROLE_ADMIN]");
                        Admin.setSolde(Admin.getSolde()+prix);
                        if (V.getDemandeSolde()<=0)
                        {   V.setSolde(V.getSolde()-V.getDemandeSolde());
                            Admin.setSolde(Admin.getSolde()+V.getDemandeSolde());
                            V.setDemandeSolde(0);
                        }

                        userRepository.save(Admin);
                    }
                    // ordre.setPrixPassage(prix);
                    ordreRepository.save(ordre);
                    userRepository.save(A);
                    userRepository.save(V);
                    c = c - ListeOrd.get(i).getNbreOrdre();
                    ListeOrd.get(i).setStatus(true);
                    //ListeOrd.get(i).setPrixPassage(prix);
                    ListeOrd.get(i).setDatePeremption(new Date());
                    ordreRepository.save(ListeOrd.get(i));
                    transaction.setDate(new Date());
                    transaction.setAcheteur(A);
                    transaction.setVendeur(V);
                    transaction.setTitre(ordre.getTitre());
                    transactionRepository.save(transaction);


                }
            }

            if(c==0)
            {   ordre.setStatus(true);
                ordre.setDatePeremption(new Date());

                ordreRepository.save(ordre);
            }
            else if(c<ordre.getNbreOrdre())
            {
                int intValue = (int) c;
                Ordre o=new Ordre();
                o.setStatus(false);
                o.setDatePeremption(null);
                o.setNbreOrdre(intValue);
                o.setDateEmission(new Date());
                o.setSensOrdre(ordre.getSensOrdre());
                o.setTitre(ordre.getTitre());
                o.setSensOrdre(ordre.getSensOrdre());
                o.setTypeOrdre(ordre.getTypeOrdre());
                o.setUsers(ordre.getUsers());
                o.setPrixPassage(o.getNbreOrdre()*(ordre.getPrixPassage()/ordre.getNbreOrdre()));
                ordreRepository.save(o);
                double prixparOrdre=ordre.getPrixPassage()/ordre.getNbreOrdre();
                ordre.setNbreOrdre(ordre.getNbreOrdre()-intValue);
                ordre.setDatePeremption(new Date());
                ordre.setStatus(true);
                ordre.setPrixPassage(ordre.getNbreOrdre()*prixparOrdre);
                ordreRepository.save(ordre);

            }
        }}


    @Override
    public void addTransactionCL(Ordre ordre) {
        List<Ordre> ListeOrd ;
        int i ;


        ListeOrd = iOrdre.listOpposeParOrdre(ordre);
        double c = ordre.getNbreOrdre();
        String type = String.valueOf(ordre.getSensOrdre());
        double limitPrice = ordre.getLimitPrice();

        if (type.equals("VENTE") && !ordre.isStatus()&& ordre.getTypeOrdre()== TypeOrdre.ACOURSLIMITE){
            for (i = 0; i < ListeOrd.size(); i++) {
                double marketPrice = ListeOrd.get(i).getLimitPrice();
                Transaction transaction = new Transaction();
                User AC;
                User VE ;
                if (ListeOrd.get(i).getLimitPrice() < limitPrice) {
                    continue;
                }
                if (c >= ListeOrd.get(i).getNbreOrdre()) {
                    VE = ordre.getUsers();
                    AC = ListeOrd.get(i).getUsers();
                    AC.getPortefeuille().getTitreListP().add(ListeOrd.get(i).getTitre());
                    AC.setSolde(AC.getSolde()-(ListeOrd.get(i).getNbreOrdre() * marketPrice));
                    if (VE.getDemandeSolde()<=0)
                    {   VE.setSolde(VE.getSolde()-VE.getDemandeSolde());
                        VE.setDemandeSolde(0);
                        VE.setSolde(VE.getSolde()+(ListeOrd.get(i).getNbreOrdre() * marketPrice));
                    }
                    else
                    {   VE.setDemandeSolde(VE.getDemandeSolde()-(ListeOrd.get(i).getNbreOrdre() * marketPrice));
                        VE.setSolde(VE.getSolde()-(ListeOrd.get(i).getNbreOrdre() * marketPrice));
                        User Admin=userRepository.findUserByRolesName("[ROLE_TRADER]");
                        Admin.setSolde(Admin.getSolde()+(ListeOrd.get(i).getNbreOrdre() * marketPrice));
                        if(VE.getDemandeSolde()<=0)
                        {   VE.setSolde(VE.getSolde()-VE.getDemandeSolde());
                            Admin.setSolde(Admin.getSolde()+VE.getDemandeSolde());
                            VE.setDemandeSolde(0);
                        }

                        userRepository.save(Admin);
                    }
                    userRepository.save(AC);
                    userRepository.save(VE);
                    c = c - ListeOrd.get(i).getNbreOrdre();
                    ListeOrd.get(i).setStatus(true);
                    ListeOrd.get(i).setDatePeremption(new Date());
                    //ordre.setPrixPassage(ListeOrd.get(i).getNbreOrdre() * marketPrice);
                    ordreRepository.save(ordre);
                    ordreRepository.save(ListeOrd.get(i));
                    transaction.setDate(new Date());
                    transaction.setAcheteur(AC);
                    transaction.setVendeur(VE);
                    transaction.setTitre(ordre.getTitre());
                    transactionRepository.save(transaction);

                }

            }
            if(c==0)
            {   ordre.setStatus(true);
                ordre.setDatePeremption(new Date());
                ordreRepository.save(ordre);
            }
            else if(c <ordre.getNbreOrdre())
            {
                int intValue = (int) c;
                Ordre o=new Ordre();
                o.setStatus(false);
                o.setDatePeremption(null);
                o.setNbreOrdre(intValue);
                o.setDateEmission(new Date());
                o.setSensOrdre(ordre.getSensOrdre());
                o.setTitre(ordre.getTitre());
                o.setSensOrdre(ordre.getSensOrdre());
                o.setTypeOrdre(ordre.getTypeOrdre());
                o.setUsers(ordre.getUsers());
                o.setLimitPrice(ordre.getLimitPrice());
                o.setPrixPassage(o.getNbreOrdre()*(ordre.getPrixPassage()/ordre.getNbreOrdre()));
                ordreRepository.save(o);
                double prixparOrdre=ordre.getPrixPassage()/ordre.getNbreOrdre();
                ordre.setNbreOrdre(ordre.getNbreOrdre()-intValue);
                ordre.setDatePeremption(new Date());
                ordre.setStatus(true);
                ordre.setPrixPassage(ordre.getNbreOrdre()*prixparOrdre);
                ordreRepository.save(ordre);


            }

        }
    }



    @Override
    public List<Transaction> getTranscationByUser(int id) {
        return transactionRepository.getallbyclientid(id);
    }




    @Override
    public List<Transaction> selectAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction SelectById(int id) {
        return transactionRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
        transactionRepository.deleteById(id);
    }


}
