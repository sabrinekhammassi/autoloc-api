package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;

public interface IReservationService {
    Reservation create(Reservation entity);
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation update(Long id, Reservation entity);
    void deleteById(Long id);

    ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom);
    ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO);
}
