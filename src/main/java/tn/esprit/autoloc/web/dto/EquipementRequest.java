package tn.esprit.autoloc.web.dto;

import lombok.Getter;
import lombok.Setter;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.Equipement;

@Getter
@Setter
public class EquipementRequest {
    private String libelle;
    private Vehicule vehicule;

    public Equipement toEntity() {
        Equipement entity = new Equipement();
        entity.setLibelle(getLibelle());
        entity.setVehicule(getVehicule());
        return entity;
    }
}
