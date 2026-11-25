package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.ReservationRequest;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;

@Component
@RequiredArgsConstructor
public class ReservationMapper {
    private final IVehiculeRepository vehiculeRepository;
    private final IClientRepository clientRepository;

    public Reservation toEntity(ReservationRequest request) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(request.dateDebut());
        reservation.setDateFin(request.dateFin());
        reservation.setStatut(request.statut());
        reservation.setVehicule(vehiculeRepository.findById(request.vehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", request.vehiculeId())));
        reservation.setClient(clientRepository.findById(request.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", request.clientId())));
        return reservation;
    }

    public Reservation toEntity(ReservationWithContratRequestDTO request, Vehicule vehicule, Client client) {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(request.dateDebut());
        reservation.setDateFin(request.dateFin());
        reservation.setStatut(request.statut());
        reservation.setVehicule(vehicule);
        reservation.setClient(client);
        return reservation;
    }

    public ReservationResponseDTO toResponse(Reservation reservation) {
        return new ReservationResponseDTO(reservation.getIdReservation(), reservation.getDateDebut(),
                reservation.getDateFin(), reservation.getStatut(),
                reservation.getVehicule() == null ? null : reservation.getVehicule().getIdVehicule(),
                reservation.getClient() == null ? null : reservation.getClient().getIdClient());
    }
}
