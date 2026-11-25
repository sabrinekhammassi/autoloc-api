package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;
import tn.esprit.autoloc.web.mapper.ContratMapper;
import tn.esprit.autoloc.web.mapper.ReservationMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository repository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final ContratMapper contratMapper;
    private final ReservationMapper mapper;

    @Override
    public Reservation create(Reservation entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Reservation est obligatoire");
        }
        if (entity.getIdReservation() != null) {
            throw new IllegalArgumentException("Un nouvel objet Reservation ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return repository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Reservation est obligatoire");
        }
        Reservation existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        validate(entity);
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setStatut(entity.getStatut());
        existing.setVehicule(entity.getVehicule());
        existing.setClient(entity.getClient());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Reservation", id);
        }
        repository.deleteById(id);
    }

    @Override
    public ReservationResponseDTO affecterReservationAClient(Long idReservation, String nom, String prenom) {
        if (nom == null || nom.isBlank() || prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException("nom et prenom sont obligatoires");
        }

        Reservation reservation = repository.findById(idReservation)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation", idReservation));
        List<Client> clients = clientRepository.findAllByNomIgnoreCaseAndPrenomIgnoreCase(nom.trim(), prenom.trim());
        if (clients.isEmpty()) {
            throw new ResourceNotFoundException("Client introuvable pour " + nom + " " + prenom);
        }
        if (clients.size() > 1) {
            throw new IllegalArgumentException("Plusieurs clients correspondent au nom et prenom fournis");
        }

        reservation.setClient(clients.get(0));
        return mapper.toResponse(repository.save(reservation));
    }

    @Override
    public ReservationResponseDTO creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requete reservation-contrat est obligatoire");
        }

        Vehicule vehicule = vehiculeRepository.findById(requestDTO.vehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", requestDTO.vehiculeId()));
        Client client = clientRepository.findById(requestDTO.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", requestDTO.clientId()));

        Reservation reservation = mapper.toEntity(requestDTO, vehicule, client);
        validate(reservation);
        Reservation savedReservation = repository.save(reservation);

        Contrat contrat = contratMapper.toEntity(requestDTO, savedReservation);
        validateContrat(contrat);
        Contrat savedContrat = contratRepository.save(contrat);
        savedReservation.setContrat(savedContrat);

        return mapper.toResponse(savedReservation);
    }

    private void validate(Reservation entity) {
        required(entity.getDateDebut(), "dateDebut");
        required(entity.getDateFin(), "dateFin");
        if (entity.getDateFin().isBefore(entity.getDateDebut())) {
            throw new IllegalArgumentException("dateFin ne peut pas preceder dateDebut");
        }
        required(entity.getStatut(), "statut");
        required(entity.getVehicule(), "vehicule");
        required(entity.getClient(), "client");
    }

    private void validateContrat(Contrat contrat) {
        required(contrat.getDateSignature(), "dateSignature");
        if (contrat.getMontantTotal() == null || contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException("montantTotal doit etre positif ou nul");
        }
        required(contrat.getReservation(), "reservation");
    }

    private static void required(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }
}
