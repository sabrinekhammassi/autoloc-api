package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.ModePaiement;
import tn.esprit.autoloc.domain.Paiement;

@Getter
@Setter
public class PaiementRequest {
    private BigDecimal montant;
    private LocalDate datePaiement;
    private ModePaiement modePaiement;
    private Contrat contrat;

    public Paiement toEntity() {
        Paiement paiement = new Paiement();
        paiement.setMontant(montant);
        paiement.setDatePaiement(datePaiement);
        paiement.setModePaiement(modePaiement);
        paiement.setContrat(contrat);
        return paiement;
    }
}
