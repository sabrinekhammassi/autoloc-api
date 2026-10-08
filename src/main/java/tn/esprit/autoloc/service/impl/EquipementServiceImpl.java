package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.service.IEquipementService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository repository;
    @Override public Equipement create(Equipement entity) { return repository.save(entity); }
    @Override @Transactional(readOnly = true) public List<Equipement> findAll() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Equipement findById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Equipement introuvable : " + id)); }
    @Override public Equipement update(Long id, Equipement entity) {
        Equipement existing = findById(id);
        existing.setLibelle(entity.getLibelle());
        existing.setVehicule(entity.getVehicule());
        return repository.save(existing);
    }
    @Override public void delete(Long id) {
        if (!repository.existsById(id)) throw new NoSuchElementException("Equipement introuvable : " + id);
        repository.deleteById(id);
    }
}