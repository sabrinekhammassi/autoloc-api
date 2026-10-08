package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Employe;
import java.util.List;

public interface IEmployeService {
    Employe create(Employe entity);
    List<Employe> findAll();
    Employe findById(Long id);
    Employe update(Long id, Employe entity);
    void delete(Long id);
}