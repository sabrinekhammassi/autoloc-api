package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.service.IAgenceService;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository repository;
    @Override
    public Agence create(Agence entity) {
        if (entity.getIdAgence() != null) throw new IllegalArgumentException("Un nouveau Agence ne doit pas avoir d'identifiant");
        validate(entity);
        return repository.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public Agence findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id)); }
    @Override
    @Transactional(readOnly = true)
    public List<Agence> findAll() { return repository.findAll(); }
    @Override
    public Agence update(Long id, Agence entity) {
        Agence existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id));
        validate(entity);
        existing.setNom(entity.getNom());
        existing.setVille(entity.getVille());
        existing.setAdresse(entity.getAdresse());
        existing.setTelephone(entity.getTelephone());
        return repository.save(existing);
    }
    @Override
    public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Agence", id); repository.deleteById(id); }
    private void validate(Agence e) { blank(e.getNom(),"nom"); blank(e.getVille(),"ville"); blank(e.getAdresse(),"adresse"); }
    private static void required(Object v,String f) { if(v==null) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void blank(String v,String f) { if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void nonNegative(java.math.BigDecimal v,String f) { if(v==null||v.signum()<0) throw new IllegalArgumentException(f+" doit être positif ou nul"); }
}
