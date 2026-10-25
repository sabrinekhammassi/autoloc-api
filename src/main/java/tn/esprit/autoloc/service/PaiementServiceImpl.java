package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements IPaiementService {
    private final IPaiementRepository repository;

    @Override
    public Paiement create(Paiement entity) {
        if (entity == null || entity.getMontant() == null || entity.getDatePaiement() == null
                || entity.getModePaiement() == null || entity.getContrat() == null) {
            throw new IllegalArgumentException("montant, datePaiement, modePaiement et contrat sont obligatoires");
        }
        if (entity.getIdPaiement() != null) {
            throw new IllegalArgumentException("Un nouveau paiement ne doit pas avoir d'identifiant");
        }
        if (entity.getMontant().signum() < 0) {
            throw new IllegalArgumentException("montant doit etre positif ou nul");
        }
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Paiement findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Paiement> findAll() {
        return repository.findAll();
    }

    @Override
    public Paiement update(Long id, Paiement entity) {
        Paiement existing = findById(id);
        if (entity == null || entity.getMontant() == null || entity.getDatePaiement() == null
                || entity.getModePaiement() == null || entity.getContrat() == null) {
            throw new IllegalArgumentException("montant, datePaiement, modePaiement et contrat sont obligatoires");
        }
        if (entity.getMontant().signum() < 0) {
            throw new IllegalArgumentException("montant doit etre positif ou nul");
        }
        existing.setMontant(entity.getMontant());
        existing.setDatePaiement(entity.getDatePaiement());
        existing.setModePaiement(entity.getModePaiement());
        existing.setContrat(entity.getContrat());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Paiement", id);
        }
        repository.deleteById(id);
    }
}
