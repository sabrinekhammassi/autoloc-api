package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.service.IMaintenanceService;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository repository;
    @Override
    public Maintenance create(Maintenance entity) {
        if (entity.getIdMaintenance() != null) throw new IllegalArgumentException("Un nouveau Maintenance ne doit pas avoir d'identifiant");
        validate(entity);
        return repository.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public Maintenance findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance", id)); }
    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> findAll() { return repository.findAll(); }
    @Override
    public Maintenance update(Long id, Maintenance entity) {
        Maintenance existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        validate(entity);
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setDescription(entity.getDescription());
        existing.setVehicule(entity.getVehicule());
        return repository.save(existing);
    }
    @Override
    public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Maintenance", id); repository.deleteById(id); }
    private void validate(Maintenance e) { required(e.getDateDebut(),"dateDebut"); blank(e.getDescription(),"description"); required(e.getVehicule(),"vehicule"); if(e.getDateFin()!=null && e.getDateFin().isBefore(e.getDateDebut())) throw new IllegalArgumentException("dateFin antérieure à dateDebut"); }
    private static void required(Object v,String f) { if(v==null) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void blank(String v,String f) { if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void nonNegative(java.math.BigDecimal v,String f) { if(v==null||v.signum()<0) throw new IllegalArgumentException(f+" doit être positif ou nul"); }
}
