package tn.esprit.autoloc.service;

import java.util.List;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

public interface IVehiculeService {
    Vehicule create(Vehicule entity);
    Vehicule findById(Long id);
    List<Vehicule> findAll();
    Vehicule update(Long id, Vehicule entity);
    void deleteById(Long id);

    VehiculeDTO affecterVehiculeAEquipement(Long idVehicule, Long idEquipement);
    VehiculeDTO affecterMaintenanceAvehicule(String description, String immatriculation);
}
