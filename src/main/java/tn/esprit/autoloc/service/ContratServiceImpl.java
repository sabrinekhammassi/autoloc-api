package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {
    private final IContratRepository repository;

    @Override
    public Contrat create(Contrat entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Contrat est obligatoire");
        }
        if (entity.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouvel objet Contrat ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> findAll() {
        return repository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Contrat est obligatoire");
        }
        Contrat existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        validate(entity);
        existing.setDateSignature(entity.getDateSignature());
        existing.setMontantTotal(entity.getMontantTotal());
        existing.setValide(entity.isValide());
        existing.setReservation(entity.getReservation());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        repository.deleteById(id);
    }

    private void validate(Contrat entity) {
        required(entity.getDateSignature(), "dateSignature");
        nonNegative(entity.getMontantTotal(), "montantTotal");
        required(entity.getReservation(), "reservation");
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
