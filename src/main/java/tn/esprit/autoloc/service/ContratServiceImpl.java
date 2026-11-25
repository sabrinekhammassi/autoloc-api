package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.web.dto.ContratDTO;
import tn.esprit.autoloc.web.mapper.ContratMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {
    private final IContratRepository repository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper mapper;

    @Override
    public Contrat create(Contrat entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Contrat est obligatoire");
        }
        if (entity.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouvel objet Contrat ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrat findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> findAll() {
        return repository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Contrat est obligatoire");
        }
        Contrat existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
        validate(entity);
        existing.setDateSignature(entity.getDateSignature());
        existing.setMontantTotal(entity.getMontantTotal());
        existing.setValide(entity.isValide());
        existing.setReservation(entity.getReservation());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        repository.deleteById(id);
    }

    @Override
    public ContratDTO affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        required(dateSignature, "dateSignature");
        required(dateDebut, "dateDebut");
        required(dateFin, "dateFin");
        if (dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("dateFin ne peut pas preceder dateDebut");
        }

        List<Reservation> reservations = reservationRepository.findAllByDateDebutAndDateFin(dateDebut, dateFin);
        if (reservations.isEmpty()) {
            throw new ResourceNotFoundException("Reservation introuvable pour la periode demandee");
        }
        if (reservations.size() > 1) {
            throw new IllegalArgumentException(
                    "Plusieurs reservations correspondent a cette periode; impossible de choisir un contrat");
        }

        Reservation reservation = reservations.get(0);
        if (repository.existsByReservation_IdReservation(reservation.getIdReservation())) {
            throw new IllegalArgumentException("Cette reservation possede deja un contrat");
        }

        long jours = Math.max(1, ChronoUnit.DAYS.between(dateDebut, dateFin));
        Contrat contrat = new Contrat();
        contrat.setDateSignature(dateSignature);
        contrat.setMontantTotal(reservation.getVehicule().getTarifJournalier()
                .multiply(java.math.BigDecimal.valueOf(jours)).setScale(2, RoundingMode.HALF_UP));
        contrat.setValide(true);
        contrat.setReservation(reservation);
        validate(contrat);

        Contrat saved = repository.save(contrat);
        reservation.setContrat(saved);
        return mapper.toResponse(saved);
    }

    private void validate(Contrat entity) {
        required(entity.getDateSignature(), "dateSignature");
        nonNegative(entity.getMontantTotal(), "montantTotal");
        required(entity.getReservation(), "reservation");
    }

    private static void required(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }

    private static void nonNegative(java.math.BigDecimal value, String field) {
        if (value == null || value.signum() < 0) {
            throw new IllegalArgumentException(field + " doit etre positif ou nul");
        }
    }
}
