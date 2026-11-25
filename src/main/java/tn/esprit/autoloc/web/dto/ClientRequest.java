package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ClientRequest(
        @NotBlank @Size(max = 50) String nom,
        @NotBlank @Size(max = 50) String prenom,
        @NotBlank @Email @Size(max = 150) String email,
        @Size(max = 20) String telephone,
        @NotBlank @Size(max = 30) String numPermis,
        @NotNull @PastOrPresent LocalDate dateInscription) {
}
