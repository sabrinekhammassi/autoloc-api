package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;

public record ReservationRequest(
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin,
        @NotNull StatutReservation statut,
        @NotNull @Positive Long vehiculeId,
        @NotNull @Positive Long clientId) {

    @AssertTrue(message = "dateFin ne peut pas preceder dateDebut")
    public boolean isDateRangeValid() {
        return dateDebut == null || dateFin == null || !dateFin.isBefore(dateDebut);
    }
}
