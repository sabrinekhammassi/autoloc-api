package tn.esprit.autoloc.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;

@Getter
@Setter
public class ReservationRequest {
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutReservation statut;
    private Vehicule vehicule;
    private Client client;

    public Reservation toEntity() {
        Reservation entity = new Reservation();
        entity.setDateDebut(getDateDebut());
        entity.setDateFin(getDateFin());
        entity.setStatut(getStatut());
        entity.setVehicule(getVehicule());
        entity.setClient(getClient());
        return entity;
    }
}
