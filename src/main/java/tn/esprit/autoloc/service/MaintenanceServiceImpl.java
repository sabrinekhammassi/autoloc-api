package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository repository;

    @Override
    public Maintenance create(Maintenance entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Maintenance est obligatoire");
        }
        if (entity.getIdMaintenance() != null) {
            throw new IllegalArgumentException("Un nouvel objet Maintenance ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Maintenance findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> findAll() {
        return repository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Maintenance est obligatoire");
        }
        Maintenance existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
        validate(entity);
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setDescription(entity.getDescription());
        existing.setVehicule(entity.getVehicule());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        repository.deleteById(id);
    }

    private void validate(Maintenance entity) {
        required(entity.getDateDebut(), "dateDebut");
        notBlank(entity.getDescription(), "description");
        required(entity.getVehicule(), "vehicule");
        if (entity.getDateFin() != null && entity.getDateFin().isBefore(entity.getDateDebut())) {
            throw new IllegalArgumentException("dateFin ne peut pas preceder dateDebut");
        }
    }

    private static void notBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }

    private static void required(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }
}
