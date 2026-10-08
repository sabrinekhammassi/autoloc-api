package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Agence;
import java.util.List;

public interface IAgenceService {
    Agence create(Agence entity);
    List<Agence> findAll();
    Agence findById(Long id);
    Agence update(Long id, Agence entity);
    void delete(Long id);
}