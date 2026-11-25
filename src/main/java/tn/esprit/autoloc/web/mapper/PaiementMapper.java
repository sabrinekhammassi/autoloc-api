package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.web.dto.PaiementRequest;
import tn.esprit.autoloc.web.dto.PaiementResponse;

@Component
@RequiredArgsConstructor
public class PaiementMapper {
    private final IContratRepository contratRepository;

    public Paiement toEntity(PaiementRequest request) {
        Paiement paiement = new Paiement();
        paiement.setMontant(request.montant());
        paiement.setDatePaiement(request.datePaiement());
        paiement.setModePaiement(request.modePaiement());
        paiement.setContrat(contratRepository.findById(request.contratId())
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", request.contratId())));
        return paiement;
    }

    public PaiementResponse toResponse(Paiement paiement) {
        return new PaiementResponse(paiement.getIdPaiement(), paiement.getMontant(), paiement.getDatePaiement(),
                paiement.getModePaiement(), paiement.getContrat() == null ? null : paiement.getContrat().getIdContrat());
    }
}
