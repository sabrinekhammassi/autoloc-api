package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.repository.IPaiementRepository;
import tn.esprit.autoloc.service.IPaiementService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements IPaiementService {
    private final IPaiementRepository repository;
    @Override public Paiement create(Paiement entity) { return repository.save(entity); }
    @Override @Transactional(readOnly = true) public List<Paiement> findAll() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Paiement findById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Paiement introuvable : " + id)); }
    @Override public Paiement update(Long id, Paiement entity) {
        Paiement existing = findById(id);
        existing.setMontant(entity.getMontant());
        existing.setDatePaiement(entity.getDatePaiement());
        existing.setModePaiement(entity.getModePaiement());
        existing.setContrat(entity.getContrat());
        return repository.save(existing);
    }
    @Override public void delete(Long id) {
        if (!repository.existsById(id)) throw new NoSuchElementException("Paiement introuvable : " + id);
        repository.deleteById(id);
    }
}