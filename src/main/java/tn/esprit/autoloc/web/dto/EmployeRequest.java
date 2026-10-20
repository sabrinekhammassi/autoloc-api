package tn.esprit.autoloc.web.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.autoloc.domain.RoleEmploye;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;

@Getter
@Setter
public class EmployeRequest {
    private String nom;
    private String prenom;
    private RoleEmploye role;
    private Agence agence;

    public Employe toEntity() {
        Employe entity = new Employe();
        entity.setNom(getNom());
        entity.setPrenom(getPrenom());
        entity.setRole(getRole());
        entity.setAgence(getAgence());
        return entity;
    }
}
