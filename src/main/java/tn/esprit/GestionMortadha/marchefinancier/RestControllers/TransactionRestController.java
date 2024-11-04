/*package tn.esprit.GestionMortadha.marchefinancier.RestControllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class TransactionRestController {
    private ITransaction iTransaction;

    @GetMapping("afficherTransaction")
    public List<Transaction> afficher()
    {
        return iTransaction.selectAll();
    }

    @PostMapping("/addTransaction")
    public void addTransaction(@RequestBody Ordre ordre)
    {
        iTransaction.addTransaction(ordre);
    }
    @PostMapping("/addTransactionCL")
    public void addTransactionCL(@RequestBody Ordre ordre)
    {
        iTransaction.addTransaction(ordre);
    }
    @GetMapping("/user/afficherTransactionAV/{id}")
    public List<Transaction> afficherTransactionAV(@PathVariable int id) {return iTransaction.getTranscationByUser(id); }


    @GetMapping("/afficherTransactionByID/{id}")
    public Transaction afficherTransactionByID(@PathVariable int id)
    {
        return iTransaction.SelectById(id);
    }
}*/


