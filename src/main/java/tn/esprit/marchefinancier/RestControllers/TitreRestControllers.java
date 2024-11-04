package tn.esprit.marchefinancier.RestControllers;

import org.springframework.web.bind.annotation.*;
import tn.esprit.marchefinancier.Entites.Titre;
import tn.esprit.marchefinancier.Service.ITitre;
import tn.esprit.marchefinancier.Service.UserExcelExporter;

import java.io.IOException;
import java.util.List;

public class TitreRestControllers {
    private ITitre iTitre;
    @GetMapping("afficherTitre")
    public List<Titre> afficher()
    {
        return iTitre.selectAll();
    }
    @DeleteMapping("/admin/deleteTitre/{id}")
    public void delete(@PathVariable int id)
    {
        iTitre.deleteById(id);
    }
    @PostMapping("/admin/addTitre")
    public Titre ajouter(@RequestBody Titre titre)
    {
        return iTitre.add(titre);
    }
    @PutMapping("/admin/updateTitre/{id}")
    public Titre update(@RequestBody Titre titre, @PathVariable int id)
    {   Titre p=iTitre.SelectById(id);
        p.setTypeTitre(titre.getTypeTitre());
        p.setBas(titre.getBas());
        p.setHaut(titre.getHaut());
        p.setDernier(titre.getDernier());
        p.setPrix(titre.getPrix());
        p.setOuverture(titre.getOuverture());
        p.setDureeValidite(titre.getDureeValidite());
        p.setNominal(titre.getNominal());
        p.setSociete(titre.getSociete());
        return iTitre.edit(p);
    }

    @GetMapping("/afficherTitreByID/{id}")
    public Titre affichertitreByID(@PathVariable int id)
    {
        return iTitre.SelectById(id);
    }
    @GetMapping("/affichertitreByIDSociete/{id}")
    public Titre affichertitreByIDSociete(@PathVariable int id)
    {
        return iTitre.findbyidSociete(id);
    }
    @GetMapping("/user/gettitrebyPortefeuille/{id}")
    public  List<Titre> gettitrebyPortefeuille(@PathVariable int id){
        return iTitre.gettitrebyPortefeuille(id);
    }

    @GetMapping("/getdata/{id}")
    public List<List<Double>> test(@PathVariable int id) throws IOException {
        UserExcelExporter excelExporter = new UserExcelExporter();
        return excelExporter.getMapsfromEXCEL(id);

    }
    @GetMapping("/getDataForMonths/{id}")
    public List<List<Double>> test1(@PathVariable int id) throws IOException {
        UserExcelExporter excelExporter = new UserExcelExporter();
        return excelExporter.getDataForMonths(id);

    }

    @GetMapping("/getDataForWeeks/{id}")
    public List<List<Double>> getDataForWeeks(@PathVariable int id) throws IOException {
        UserExcelExporter excelExporter = new UserExcelExporter();
        return excelExporter.getDataForWeeks(id);

    }
    @GetMapping("/getDataForYears/{id}")
    public List<List<Double>> getDataForYears(@PathVariable int id) throws IOException {
        UserExcelExporter excelExporter = new UserExcelExporter();
        return excelExporter.getDataForYears(id);

    }

    @GetMapping("critere/{idtitre}")
    public double calculeMarkowvitz(@PathVariable int idtitre) throws IOException {
        return iTitre.RisqueMarkowvitz(idtitre);
    }
//    @PostMapping("/run-python-script")
//    public String runPythonScript() throws IOException {
//        // Exécutez le script Python depuis le serveur
//        Process process = Runtime.getRuntime().exec("python C:/Users/ASUS/OneDrive/Bureau/chandelier.py");
//        return "Le script Python a été exécuté avec succès.";
//    }

}
