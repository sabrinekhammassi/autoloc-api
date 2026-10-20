package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IReservationRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository repository;

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

    private static void required(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }
}
