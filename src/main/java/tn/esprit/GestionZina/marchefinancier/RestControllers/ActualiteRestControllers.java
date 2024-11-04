package tn.esprit.GestionZina.marchefinancier.RestControllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.GestionZina.marchefinancier.Entites.Actualite;
import tn.esprit.GestionZina.marchefinancier.Service.IActualite;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class ActualiteRestControllers {
    private IActualite iActualite;
    @GetMapping("afficherActualite")
    public List<Actualite> afficher()
    {
        return iActualite.selectAll();
    }
    @DeleteMapping("/admin/deleteActualite/{id}")
    public void delete(@PathVariable int id)
    {
        iActualite.deleteById(id);
    }
    @PutMapping("/admin/updateActualite/{id}")
    public Actualite update(@RequestBody Actualite actualite, @PathVariable int id)
    {   Actualite p =iActualite.SelectById(id);
        p.setDescription(actualite.getDescription());
        p.setTitre(actualite.getTitre());
        return iActualite.edit(p);
    }
    @PostMapping("/admin/addActualite")
    public Actualite ajouter(@RequestBody Actualite actualité)
    {
        return iActualite.add(actualité);
    }
    @GetMapping("/afficherActualiteByID/{id}")
    public Actualite afficherActualiteByID(@PathVariable int id)
    {
        return iActualite.SelectById(id);
    }
    @PostMapping("/ajouterimageActualite/{id}")
    public void createSynthImage(@PathVariable("id") int id, @RequestParam("file") MultipartFile file) throws Exception {
        Actualite synthesizer = iActualite.SelectById(id);
        // The "public" directory is automatically used by Spring to serve static assets
        Path publicDirectory = Paths.get( "public").toAbsolutePath();
        byte[] imageContent = file.getBytes();
        Path filepath = Paths.get(publicDirectory.toString(), file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(filepath)) {
            os.write(imageContent);
        }
        synthesizer.setFilename(file.getOriginalFilename());
        iActualite.edit(synthesizer);
    }
}
