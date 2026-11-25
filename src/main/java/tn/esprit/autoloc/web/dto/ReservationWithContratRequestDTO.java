package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;

public record ReservationWithContratRequestDTO(
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin,
        @NotNull StatutReservation statut,
        @NotNull @Positive Long vehiculeId,
        @NotNull @Positive Long clientId,
        @NotNull LocalDate dateSignature,
        @NotNull @DecimalMin("0.00") @Digits(integer = 8, fraction = 2) BigDecimal montantTotal,
        boolean valide) {

    @AssertTrue(message = "dateFin ne peut pas preceder dateDebut")
    public boolean isDateRangeValid() {
        return dateDebut == null || dateFin == null || !dateFin.isBefore(dateDebut);
    }
}
