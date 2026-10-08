package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {
    private final IContratRepository repository;
    @Override public Contrat create(Contrat entity) {
        entity.getPaiements().forEach(paiement -> paiement.setContrat(entity));
        return repository.save(entity);
    }
    @Override @Transactional(readOnly = true) public List<Contrat> findAll() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Contrat findById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Contrat introuvable : " + id)); }
    @Override public Contrat update(Long id, Contrat entity) {
        Contrat existing = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Contrat introuvable : " + id));
        existing.setDateSignature(entity.getDateSignature());
        existing.setMontantTotal(entity.getMontantTotal());
        existing.setValide(entity.isValide());
        existing.setReservation(entity.getReservation());
        existing.getPaiements().clear();
        entity.getPaiements().forEach(paiement -> {
            paiement.setContrat(existing);
            existing.getPaiements().add(paiement);
        });
        return repository.save(existing);
    }
    @Override public void delete(Long id) {
        if (!repository.existsById(id)) throw new NoSuchElementException("Contrat introuvable : " + id);
        repository.deleteById(id);
    }
}
