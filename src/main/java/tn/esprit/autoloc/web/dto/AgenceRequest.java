package tn.esprit.autoloc.web.dto;

import tn.esprit.autoloc.domain.Agence;

public record AgenceRequest(String nom, String ville, String adresse, String telephone) {
    public Agence toEntity() {
        Agence agence = new Agence();
        agence.setNom(nom);
        agence.setVille(ville);
        agence.setAdresse(adresse);
        agence.setTelephone(telephone);
        return agence;
    }
}
