package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.web.dto.ContratRequest;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;

@Component
@RequiredArgsConstructor
public class ContratMapper {
    private final IReservationRepository reservationRepository;

    public Contrat toEntity(ContratRequest request) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(request.dateSignature());
        contrat.setMontantTotal(request.montantTotal());
        contrat.setValide(request.valide());
        contrat.setReservation(reservationRepository.findById(request.reservationId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", request.reservationId())));
        return contrat;
    }

    public Contrat toEntity(ReservationWithContratRequestDTO request, Reservation reservation) {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(request.dateSignature());
        contrat.setMontantTotal(request.montantTotal());
        contrat.setValide(request.valide());
        contrat.setReservation(reservation);
        return contrat;
    }

    public ContratDTO toResponse(Contrat contrat) {
        return new ContratDTO(contrat.getIdContrat(), contrat.getDateSignature(), contrat.getMontantTotal(),
                contrat.isValide(), contrat.getReservation() == null ? null : contrat.getReservation().getIdReservation());
    }
}
