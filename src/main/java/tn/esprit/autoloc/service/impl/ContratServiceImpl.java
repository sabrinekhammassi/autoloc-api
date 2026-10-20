package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.service.IContratService;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {
    private final IContratRepository repository;
    @Override
    public Contrat create(Contrat entity) {
        if (entity.getIdContrat() != null) throw new IllegalArgumentException("Un nouveau Contrat ne doit pas avoir d'identifiant");
        validate(entity);
        return repository.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public Contrat findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat", id)); }
    @Override
    @Transactional(readOnly = true)
    public List<Contrat> findAll() { return repository.findAll(); }
    @Override
    public Contrat update(Long id, Contrat entity) {
        Contrat existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        validate(entity);
        existing.setDateSignature(entity.getDateSignature());
        existing.setMontantTotal(entity.getMontantTotal());
        existing.setValide(entity.isValide());
        existing.setReservation(entity.getReservation());
        return repository.save(existing);
    }
    @Override
    public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Contrat", id); repository.deleteById(id); }
    private void validate(Contrat e) { required(e.getDateSignature(),"dateSignature"); nonNegative(e.getMontantTotal(),"montantTotal"); required(e.getReservation(),"reservation"); }
    private static void required(Object v,String f) { if(v==null) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void blank(String v,String f) { if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void nonNegative(java.math.BigDecimal v,String f) { if(v==null||v.signum()<0) throw new IllegalArgumentException(f+" doit être positif ou nul"); }
}
