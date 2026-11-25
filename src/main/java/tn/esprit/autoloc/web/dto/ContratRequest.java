package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratRequest(
        @NotNull LocalDate dateSignature,
        @NotNull @DecimalMin("0.00") @Digits(integer = 8, fraction = 2) BigDecimal montantTotal,
        boolean valide,
        @NotNull @Positive Long reservationId) {
}
