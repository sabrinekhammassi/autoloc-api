package tn.esprit.autoloc.service;

import java.util.List;
import java.time.LocalDate;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.web.dto.ContratDTO;

public interface IContratService {
    Contrat create(Contrat entity);
    Contrat findById(Long id);
    List<Contrat> findAll();
    Contrat update(Long id, Contrat entity);
    void deleteById(Long id);

    ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}
