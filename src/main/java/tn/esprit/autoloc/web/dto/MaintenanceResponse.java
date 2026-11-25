package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;

public record MaintenanceResponse(Long id, LocalDate dateDebut, LocalDate dateFin,
                                 String description, Long vehiculeId) {
}
