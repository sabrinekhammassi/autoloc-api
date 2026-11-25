package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;

public record ReservationResponseDTO(Long id, LocalDate dateDebut, LocalDate dateFin,
                                     StatutReservation statut, Long vehiculeId, Long clientId) {
}
