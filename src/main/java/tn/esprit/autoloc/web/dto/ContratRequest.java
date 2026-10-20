package tn.esprit.autoloc.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.math.BigDecimal;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Contrat;

@Getter
@Setter
public class ContratRequest {
    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private boolean valide;
    private Reservation reservation;

    public Contrat toEntity() {
        Contrat entity = new Contrat();
        entity.setDateSignature(getDateSignature());
        entity.setMontantTotal(getMontantTotal());
        entity.setValide(isValide());
        entity.setReservation(getReservation());
        return entity;
    }
}
