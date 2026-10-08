package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.service.IVehiculeService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository repository;
    @Override public Vehicule create(Vehicule entity) { return repository.save(entity); }
    @Override @Transactional(readOnly = true) public List<Vehicule> findAll() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Vehicule findById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Vehicule introuvable : " + id)); }
    @Override public Vehicule update(Long id, Vehicule entity) {
        Vehicule existing = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Vehicule introuvable : " + id));
        existing.setImmatriculation(entity.getImmatriculation());
        existing.setMarque(entity.getMarque());
        existing.setModele(entity.getModele());
        existing.setCategorie(entity.getCategorie());
        existing.setTarifJournalier(entity.getTarifJournalier());
        existing.setStatut(entity.getStatut());
        existing.setAgence(entity.getAgence());
        return repository.save(existing);
    }
    @Override public void delete(Long id) {
        if (!repository.existsById(id)) throw new NoSuchElementException("Vehicule introuvable : " + id);
        repository.deleteById(id);
    }
}