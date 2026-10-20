package tn.esprit.autoloc.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.Client;

@Getter
@Setter
public class ClientRequest {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String numPermis;
    private LocalDate dateInscription;

    public Client toEntity() {
        Client entity = new Client();
        entity.setNom(getNom());
        entity.setPrenom(getPrenom());
        entity.setEmail(getEmail());
        entity.setTelephone(getTelephone());
        entity.setNumPermis(getNumPermis());
        entity.setDateInscription(getDateInscription());
        return entity;
    }
}
