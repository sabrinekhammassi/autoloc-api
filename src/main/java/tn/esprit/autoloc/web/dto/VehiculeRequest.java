package tn.esprit.autoloc.web.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.autoloc.domain.CategorieVehicule;
import java.math.BigDecimal;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;

@Getter
@Setter
public class VehiculeRequest {
    private String immatriculation;
    private String marque;
    private String modele;
    private CategorieVehicule categorie;
    private BigDecimal tarifJournalier;
    private StatutVehicule statut;
    private Agence agence;

    public Vehicule toEntity() {
        Vehicule entity = new Vehicule();
        entity.setImmatriculation(getImmatriculation());
        entity.setMarque(getMarque());
        entity.setModele(getModele());
        entity.setCategorie(getCategorie());
        entity.setTarifJournalier(getTarifJournalier());
        entity.setStatut(getStatut());
        entity.setAgence(getAgence());
        return entity;
    }
}
