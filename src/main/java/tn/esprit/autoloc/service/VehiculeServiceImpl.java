package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository repository;
    @Override
    public Vehicule create(Vehicule entity) {
        if (entity.getIdVehicule() != null) throw new IllegalArgumentException("Un nouveau Vehicule ne doit pas avoir d'identifiant");
        validate(entity);
        return repository.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public Vehicule findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id)); }
    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> findAll() { return repository.findAll(); }
    @Override
    public Vehicule update(Long id, Vehicule entity) {
        Vehicule existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
        validate(entity);
        existing.setImmatriculation(entity.getImmatriculation());
        existing.setMarque(entity.getMarque());
        existing.setModele(entity.getModele());
        existing.setCategorie(entity.getCategorie());
        existing.setTarifJournalier(entity.getTarifJournalier());
        existing.setStatut(entity.getStatut());
        existing.setAgence(entity.getAgence());
        return repository.save(existing);
    }
    @Override
    public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Vehicule", id); repository.deleteById(id); }
    private void validate(Vehicule e) { blank(e.getImmatriculation(),"immatriculation"); blank(e.getMarque(),"marque"); blank(e.getModele(),"modele"); required(e.getCategorie(),"categorie"); nonNegative(e.getTarifJournalier(),"tarifJournalier"); required(e.getStatut(),"statut"); required(e.getAgence(),"agence"); }
    private static void required(Object v,String f) { if(v==null) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void blank(String v,String f) { if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void nonNegative(java.math.BigDecimal v,String f) { if(v==null||v.signum()<0) throw new IllegalArgumentException(f+" doit Ãªtre positif ou nul"); }
}

