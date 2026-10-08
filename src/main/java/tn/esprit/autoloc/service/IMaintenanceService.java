package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Maintenance;
import java.util.List;

public interface IMaintenanceService {
    Maintenance create(Maintenance entity);
    List<Maintenance> findAll();
    Maintenance findById(Long id);
    Maintenance update(Long id, Maintenance entity);
    void delete(Long id);
}