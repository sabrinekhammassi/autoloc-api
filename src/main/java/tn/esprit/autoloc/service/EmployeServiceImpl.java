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
        if (entity.getIdEmploye() != null) throw new IllegalArgumentException("Un nouveau Employe ne doit pas avoir d'identifiant");
        validate(entity);
        return repository.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public Employe findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employe", id)); }
    @Override
    @Transactional(readOnly = true)
    public List<Employe> findAll() { return repository.findAll(); }
    @Override
    public Employe update(Long id, Employe entity) {
        Employe existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employe", id));
        validate(entity);
        existing.setNom(entity.getNom());
        existing.setPrenom(entity.getPrenom());
        existing.setRole(entity.getRole());
        existing.setAgence(entity.getAgence());
        return repository.save(existing);
    }
    @Override
    public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Employe", id); repository.deleteById(id); }
    private void validate(Employe e) { blank(e.getNom(),"nom"); blank(e.getPrenom(),"prenom"); required(e.getRole(),"role"); required(e.getAgence(),"agence"); }
    private static void required(Object v,String f) { if(v==null) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void blank(String v,String f) { if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void nonNegative(java.math.BigDecimal v,String f) { if(v==null||v.signum()<0) throw new IllegalArgumentException(f+" doit Ãªtre positif ou nul"); }
}

