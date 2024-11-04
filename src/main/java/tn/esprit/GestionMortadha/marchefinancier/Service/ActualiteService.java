package tn.esprit.GestionMortadha.marchefinancier.Service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.GestionMortadha.marchefinancier.Entites.Actualite;
import tn.esprit.GestionMortadha.marchefinancier.Repositories.ActualiteRepository;
import tn.esprit.GestionMortadha.marchefinancier.Repositories.SocieteRepository;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class ActualiteService implements IActualite {
    private ActualiteRepository actualiteRepository;
    private SocieteRepository societeRepository;

    @Override
    public Actualite add(Actualite a) {
//        Societe societe=societeRepository.findByName(a.getSociete().getName());
//        a.setSociete(societe);

        return actualiteRepository.save(a);
    }

    @Override
    public Actualite edit(Actualite a) {
        return actualiteRepository.save(a);
    }

    @Override
    public List<Actualite> selectAll() {
        return actualiteRepository.findAll();
    }

    @Override
    public Actualite SelectById(int id) {
        return actualiteRepository.findById(id).get();

}

    @Override
    public void deleteById(int id) {
actualiteRepository.deleteById(id);
    }
}
