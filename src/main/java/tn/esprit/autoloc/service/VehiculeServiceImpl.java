package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository repository;

    @Override
    public Vehicule create(Vehicule entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Vehicule est obligatoire");
        }
        if (entity.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouvel objet Vehicule ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicule findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> findAll() {
        return repository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Vehicule est obligatoire");
        }
        Vehicule existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
        validate(entity);
        existing.setImmatriculation(entity.getImmatriculation());
        existing.setMarque(entity.getMarque());
        existing.setModele(entity.getModele());
        existing.setCategorie(entity.getCategorie());
        existing.setTarifJournalier(entity.getTarifJournalier());
        existing.setStatut(entity.getStatut());
        existing.setAgence(entity.getAgence());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        repository.deleteById(id);
    }

    private void validate(Vehicule entity) {
        notBlank(entity.getImmatriculation(), "immatriculation");
        notBlank(entity.getMarque(), "marque");
        notBlank(entity.getModele(), "modele");
        required(entity.getCategorie(), "categorie");
        nonNegative(entity.getTarifJournalier(), "tarifJournalier");
        required(entity.getStatut(), "statut");
        required(entity.getAgence(), "agence");
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

    private static void nonNegative(java.math.BigDecimal value, String field) {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException(field + " doit etre positif ou nul");
        }
    }
}
