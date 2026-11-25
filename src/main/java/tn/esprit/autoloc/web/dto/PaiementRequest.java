package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.ModePaiement;

public record PaiementRequest(
        @NotNull @DecimalMin("0.00") @Digits(integer = 8, fraction = 2) BigDecimal montant,
        @NotNull LocalDate datePaiement,
        @NotNull ModePaiement modePaiement,
        @NotNull @Positive Long contratId) {
}
