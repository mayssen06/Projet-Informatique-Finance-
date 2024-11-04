package tn.esprit.GestionZina.marchefinancier.Service;

import tn.esprit.GestionZina.marchefinancier.Entites.*;
import java.util.List;

public interface ITransaction {
    void addTransaction(Ordre ordre);

    List<Transaction> selectAll();
    Transaction SelectById(int id);
    void deleteById(int id);

    public void addTransactionCL(Ordre ordre);
    List<Transaction> getTranscationByUser(int id);


}
