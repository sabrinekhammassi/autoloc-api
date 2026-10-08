package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository repository;
    @Override public Agence create(Agence entity) { return repository.save(entity); }
    @Override @Transactional(readOnly = true) public List<Agence> findAll() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Agence findById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Agence introuvable : " + id)); }
    @Override public Agence update(Long id, Agence entity) {
        Agence existing = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Agence introuvable : " + id));
        existing.setNom(entity.getNom());
        existing.setVille(entity.getVille());
        existing.setAdresse(entity.getAdresse());
        existing.setTelephone(entity.getTelephone());
        return repository.save(existing);
    }
    @Override public void delete(Long id) {
        if (!repository.existsById(id)) throw new NoSuchElementException("Agence introuvable : " + id);
        repository.deleteById(id);
    }
}