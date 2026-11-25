package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;

@Component
@RequiredArgsConstructor
public class VehiculeMapper {
    private final IAgenceRepository agenceRepository;

    public Vehicule toEntity(VehiculeRequest request) {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation(request.immatriculation());
        vehicule.setMarque(request.marque());
        vehicule.setModele(request.modele());
        vehicule.setCategorie(request.categorie());
        vehicule.setTarifJournalier(request.tarifJournalier());
        vehicule.setStatut(request.statut());
        vehicule.setAgence(agenceRepository.findById(request.agenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Agence", request.agenceId())));
        return vehicule;
    }

    public VehiculeResponse toResponse(Vehicule vehicule) {
        return new VehiculeResponse(vehicule.getIdVehicule(), vehicule.getImmatriculation(), vehicule.getMarque(),
                vehicule.getModele(), vehicule.getCategorie(), vehicule.getTarifJournalier(), vehicule.getStatut(),
                vehicule.getAgence() == null ? null : vehicule.getAgence().getIdAgence());
    }
}
