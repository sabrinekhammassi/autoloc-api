package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.RoleEmploye;

public record EmployeResponse(Long id, String nom, String prenom, RoleEmploye role, Long agenceId) {
}
