package tn.esprit.autoloc.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.Maintenance;

@Getter
@Setter
public class MaintenanceRequest {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String description;
    private Vehicule vehicule;

    public Maintenance toEntity() {
        Maintenance entity = new Maintenance();
        entity.setDateDebut(getDateDebut());
        entity.setDateFin(getDateFin());
        entity.setDescription(getDescription());
        entity.setVehicule(getVehicule());
        return entity;
    }
}
