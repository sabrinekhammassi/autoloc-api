package tn.esprit.autoloc.web.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.web.dto.EmployeRequest;
import tn.esprit.autoloc.web.dto.EmployeResponse;

@Component
@RequiredArgsConstructor
public class EmployeMapper {
    private final IAgenceRepository agenceRepository;

    public Employe toEntity(EmployeRequest request) {
        Employe employe = new Employe();
        employe.setNom(request.nom());
        employe.setPrenom(request.prenom());
        employe.setRole(request.role());
        employe.setAgence(agenceRepository.findById(request.agenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Agence", request.agenceId())));
        return employe;
    }

    public EmployeResponse toResponse(Employe employe) {
        return new EmployeResponse(employe.getIdEmploye(), employe.getNom(), employe.getPrenom(),
                employe.getRole(), employe.getAgence() == null ? null : employe.getAgence().getIdAgence());
    }
}
