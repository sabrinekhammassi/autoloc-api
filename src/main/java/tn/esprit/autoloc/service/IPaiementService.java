package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Paiement;
import java.util.List;

public interface IPaiementService {
    Paiement create(Paiement entity);
    List<Paiement> findAll();
    Paiement findById(Long id);
    Paiement update(Long id, Paiement entity);
    void delete(Long id);
}