package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository repository;

    @Override
    public Employe create(Employe entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Employe est obligatoire");
        }
        if (entity.getIdEmploye() != null) {
            throw new IllegalArgumentException("Un nouvel objet Employe ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Employe findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> findAll() {
        return repository.findAll();
    }

    @Override
    public Employe update(Long id, Employe entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Employe est obligatoire");
        }
        Employe existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
        validate(entity);
        existing.setNom(entity.getNom());
        existing.setPrenom(entity.getPrenom());
        existing.setRole(entity.getRole());
        existing.setAgence(entity.getAgence());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        repository.deleteById(id);
    }

    private void validate(Employe entity) {
        notBlank(entity.getNom(), "nom");
        notBlank(entity.getPrenom(), "prenom");
        required(entity.getRole(), "role");
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
}
