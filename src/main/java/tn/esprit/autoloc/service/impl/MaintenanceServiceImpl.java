package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.service.IMaintenanceService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository repository;
    @Override public Maintenance create(Maintenance entity) { return repository.save(entity); }
    @Override @Transactional(readOnly = true) public List<Maintenance> findAll() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Maintenance findById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Maintenance introuvable : " + id)); }
    @Override public Maintenance update(Long id, Maintenance entity) {
        Maintenance existing = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Maintenance introuvable : " + id));
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setDescription(entity.getDescription());
        existing.setVehicule(entity.getVehicule());
        return repository.save(existing);
    }
    @Override public void delete(Long id) {
        if (!repository.existsById(id)) throw new NoSuchElementException("Maintenance introuvable : " + id);
        repository.deleteById(id);
    }
}