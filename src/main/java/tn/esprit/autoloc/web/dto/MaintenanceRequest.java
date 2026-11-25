package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record MaintenanceRequest(
        @NotNull LocalDate dateDebut,
        LocalDate dateFin,
        @NotBlank @Size(max = 1000) String description,
        @NotNull @Positive Long vehiculeId) {

    @AssertTrue(message = "dateFin ne peut pas preceder dateDebut")
    public boolean isDateRangeValid() {
        return dateDebut == null || dateFin == null || !dateFin.isBefore(dateDebut);
    }
}
