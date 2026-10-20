package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository repository;

    @Override
    public Agence create(Agence entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Agence est obligatoire");
        }
        if (entity.getIdAgence() != null) {
            throw new IllegalArgumentException("Un nouvel objet Agence ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Agence findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agence> findAll() {
        return repository.findAll();
    }

    @Override
    public Agence update(Long id, Agence entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Agence est obligatoire");
        }
        Agence existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        validate(entity);
        existing.setNom(entity.getNom());
        existing.setVille(entity.getVille());
        existing.setAdresse(entity.getAdresse());
        existing.setTelephone(entity.getTelephone());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        repository.deleteById(id);
    }

    private void validate(Agence entity) {
        notBlank(entity.getNom(), "nom");
        notBlank(entity.getVille(), "ville");
        notBlank(entity.getAdresse(), "adresse");
    }

    private static void notBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }
}
