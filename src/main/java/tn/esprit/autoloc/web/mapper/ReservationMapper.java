package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.ReservationRequest;
import tn.esprit.autoloc.web.dto.ReservationResponse;

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

    public ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getIdReservation(), reservation.getDateDebut(),
                reservation.getDateFin(), reservation.getStatut(),
                reservation.getVehicule() == null ? null : reservation.getVehicule().getIdVehicule(),
                reservation.getClient() == null ? null : reservation.getClient().getIdClient());
    }
}
