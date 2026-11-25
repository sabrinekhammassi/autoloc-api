package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.EquipementRequest;
import tn.esprit.autoloc.web.dto.EquipementResponse;

@Component
@RequiredArgsConstructor
public class EquipementMapper {
    private final IVehiculeRepository vehiculeRepository;

    public Equipement toEntity(EquipementRequest request) {
        Equipement equipement = new Equipement();
        equipement.setLibelle(request.libelle());
        equipement.setVehicule(vehiculeRepository.findById(request.vehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", request.vehiculeId())));
        return equipement;
    }

    public EquipementResponse toResponse(Equipement equipement) {
        return new EquipementResponse(equipement.getIdEquipement(), equipement.getLibelle(),
                equipement.getVehicule() == null ? null : equipement.getVehicule().getIdVehicule());
    }
}
