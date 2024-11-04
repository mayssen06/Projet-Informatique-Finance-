package tn.esprit.GestionZina.marchefinancier.Service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.GestionZina.marchefinancier.Entites.Societe;
import tn.esprit.GestionZina.marchefinancier.Repositories.SocieteRepository;
import tn.esprit.GestionZina.marchefinancier.Service.ISociete;

import java.util.List;

@AllArgsConstructor
@Service
public class SocieteService implements ISociete {
    private SocieteRepository societeRepository;

    @Override
    public Societe add(Societe a) {
        return societeRepository.save(a);
    }

    @Override
    public Societe edit(Societe a) {
        return societeRepository.save(a);
    }

    @Override
    public List<Societe> selectAll() {
         return societeRepository.findAll();
    }

    @Override
    public Societe SelectById(int id) {
        return societeRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
         societeRepository.deleteById(id);
    }
    @Override
    public  Societe getSocieteByName(String name)
    {
        return societeRepository.findByName((name));

    }
}
