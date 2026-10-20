package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository repository;

    @Override
    public Equipement create(Equipement entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Equipement est obligatoire");
        }
        if (entity.getIdEquipement() != null) {
            throw new IllegalArgumentException("Un nouvel objet Equipement ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Equipement findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> findAll() {
        return repository.findAll();
    }

    @Override
    public Equipement update(Long id, Equipement entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Equipement est obligatoire");
        }
        Equipement existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
        validate(entity);
        existing.setLibelle(entity.getLibelle());
        existing.setVehicule(entity.getVehicule());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        repository.deleteById(id);
    }

    private void validate(Equipement entity) {
        notBlank(entity.getLibelle(), "libelle");
        required(entity.getVehicule(), "vehicule");
    }

    private static void notBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }

    private static void required(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }
}
