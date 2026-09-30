package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeService {

    private final VehiculeRepository repository;

    public Vehicule create(Vehicule v) {
        return repository.save(v);
    }

    public List<Vehicule> findAll() {
        return repository.findAll();
    }

    public Vehicule findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable"));
    }

    public Vehicule update(Long id, Vehicule v) {
        Vehicule existing = findById(id);

        existing.setImmatriculation(v.getImmatriculation());
        existing.setMarque(v.getMarque());
        existing.setModele(v.getModele());
        existing.setCategorie(v.getCategorie());
        existing.setTarifJournalier(v.getTarifJournalier());
        existing.setStatut(v.getStatut());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}