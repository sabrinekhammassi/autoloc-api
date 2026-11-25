package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IMaintenanceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.VehiculeDTO;
import tn.esprit.autoloc.web.mapper.VehiculeMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository repository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final VehiculeMapper mapper;

    @Override
    public Vehicule create(Vehicule entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Vehicule est obligatoire");
        }
        if (entity.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouvel objet Vehicule ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Vehicule findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> findAll() {
        return repository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Vehicule est obligatoire");
        }
        Vehicule existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
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
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        repository.deleteById(id);
    }

    @Override
    public VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = repository.findById(idVehicule)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", idVehicule));
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));

        Vehicule ancienVehicule = equipement.getVehicule();
        if (ancienVehicule != null && ancienVehicule.getEquipements() != null) {
            ancienVehicule.getEquipements().remove(equipement);
        }
        equipement.setVehicule(vehicule);
        if (vehicule.getEquipements() != null && !vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }

        equipementRepository.save(equipement);
        return mapper.toResponse(vehicule);
    }

    @Override
    public VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation) {
        notBlank(description, "description");
        notBlank(immatriculation, "immatriculation");
        if (description.trim().length() > 1000) {
            throw new IllegalArgumentException("description ne peut pas depasser 1000 caracteres");
        }

        Vehicule vehicule = repository.findByImmatriculationIgnoreCase(immatriculation.trim())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vehicule introuvable pour l'immatriculation " + immatriculation));

        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(LocalDate.now());
        maintenance.setDescription(description.trim());
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);

        if (vehicule.getMaintenances() != null) {
            vehicule.getMaintenances().add(maintenance);
        }
        vehicule.setStatut(StatutVehicule.MAINTENANCE);
        return mapper.toResponse(repository.save(vehicule));
    }

    private void validate(Vehicule entity) {
        notBlank(entity.getImmatriculation(), "immatriculation");
        notBlank(entity.getMarque(), "marque");
        notBlank(entity.getModele(), "modele");
        required(entity.getCategorie(), "categorie");
        nonNegative(entity.getTarifJournalier(), "tarifJournalier");
        required(entity.getStatut(), "statut");
        required(entity.getAgence(), "agence");
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

    private static void nonNegative(java.math.BigDecimal value, String field) {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException(field + " doit etre positif ou nul");
        }
    }
}
