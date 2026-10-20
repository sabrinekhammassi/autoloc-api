package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository repository;
    @Override
    public Equipement create(Equipement entity) {
        if (entity.getIdEquipement() != null) throw new IllegalArgumentException("Un nouveau Equipement ne doit pas avoir d'identifiant");
        validate(entity);
        return repository.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public Equipement findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id)); }
    @Override
    @Transactional(readOnly = true)
    public List<Equipement> findAll() { return repository.findAll(); }
    @Override
    public Equipement update(Long id, Equipement entity) {
        Equipement existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
        validate(entity);
        existing.setLibelle(entity.getLibelle());
        existing.setVehicule(entity.getVehicule());
        return repository.save(existing);
    }
    @Override
    public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Equipement", id); repository.deleteById(id); }
    private void validate(Equipement e) { blank(e.getLibelle(),"libelle"); required(e.getVehicule(),"vehicule"); }
    private static void required(Object v,String f) { if(v==null) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void blank(String v,String f) { if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void nonNegative(java.math.BigDecimal v,String f) { if(v==null||v.signum()<0) throw new IllegalArgumentException(f+" doit Ãªtre positif ou nul"); }
}

