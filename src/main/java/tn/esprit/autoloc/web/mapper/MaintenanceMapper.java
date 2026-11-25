package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.MaintenanceRequest;
import tn.esprit.autoloc.web.dto.MaintenanceResponse;

@Component
@RequiredArgsConstructor
public class MaintenanceMapper {
    private final IVehiculeRepository vehiculeRepository;

    public Maintenance toEntity(MaintenanceRequest request) {
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(request.dateDebut());
        maintenance.setDateFin(request.dateFin());
        maintenance.setDescription(request.description());
        maintenance.setVehicule(vehiculeRepository.findById(request.vehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", request.vehiculeId())));
        return maintenance;
    }

    public MaintenanceResponse toResponse(Maintenance maintenance) {
        return new MaintenanceResponse(maintenance.getIdMaintenance(), maintenance.getDateDebut(),
                maintenance.getDateFin(), maintenance.getDescription(),
                maintenance.getVehicule() == null ? null : maintenance.getVehicule().getIdVehicule());
    }
}
