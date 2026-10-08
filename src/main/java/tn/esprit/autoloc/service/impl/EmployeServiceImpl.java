package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;
import tn.esprit.autoloc.service.IEmployeService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository repository;
    @Override public Employe create(Employe entity) { return repository.save(entity); }
    @Override @Transactional(readOnly = true) public List<Employe> findAll() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Employe findById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Employe introuvable : " + id)); }
    @Override public Employe update(Long id, Employe entity) {
        Employe existing = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Employe introuvable : " + id));
        existing.setNom(entity.getNom());
        existing.setPrenom(entity.getPrenom());
        existing.setRole(entity.getRole());
        existing.setAgence(entity.getAgence());
        return repository.save(existing);
    }
    @Override public void delete(Long id) {
        if (!repository.existsById(id)) throw new NoSuchElementException("Employe introuvable : " + id);
        repository.deleteById(id);
    }
}