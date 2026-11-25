package tn.esprit.autoloc.web.dto;

import java.time.LocalDate;

public record ClientResponse(Long id, String nom, String prenom, String email,
                             String telephone, String numPermis, LocalDate dateInscription) {
}
