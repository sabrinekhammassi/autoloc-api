package tn.esprit.autoloc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContratResponse(Long id, LocalDate dateSignature, BigDecimal montantTotal,
                              boolean valide, Long reservationId) {
}
