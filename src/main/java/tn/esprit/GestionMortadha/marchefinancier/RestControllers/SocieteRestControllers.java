package tn.esprit.GestionMortadha.marchefinancier.RestControllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.GestionMortadha.marchefinancier.Entites.Societe;
import tn.esprit.GestionMortadha.marchefinancier.Service.ISociete;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class SocieteRestControllers {
    private ISociete iSociete;
    @GetMapping("afficherSociete")
    public List<Societe> afficher()
    {
        return iSociete.selectAll();
    }
    @DeleteMapping("/admin/deleteSociete/{id}")
    public void delete(@PathVariable int id)
    {
        iSociete.deleteById(id);
    }
    @PutMapping("/admin/updateSociete/{id}")
    public Societe update(@RequestBody Societe societe,@PathVariable int id)
    {   Societe p=iSociete.SelectById(id);
        p.setName(societe.getName());
        p.setNbrTitre(societe.getNbrTitre());
        p.setName(societe.getName());
        p.setNumeroTel(societe.getNumeroTel());
        p.setSiegeSocial(societe.getSiegeSocial());
        p.setObjetSocial(societe.getObjetSocial());
        p.setCapitalBoursier(societe.getCapitalBoursier());
        return iSociete.edit(p);
    }
    @PostMapping("/admin/addSociete")
    public Societe ajouter(@RequestBody Societe societe)
    {
        return iSociete.add(societe);
    }
    @GetMapping("/afficherSocieteByID/{id}")
    public Societe afficherSocieteByID(@PathVariable int id)
    {
        return iSociete.SelectById(id);
    }
    @GetMapping("/user/afficherSocieteByNAME/{name}")
    public Societe afficherSocieteByNAME(@PathVariable String name)
    {
        return iSociete.getSocieteByName(name);
    }
}
