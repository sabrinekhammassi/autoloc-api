package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.service.IReservationService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {
    private final IReservationRepository repository;
    @Override public Reservation create(Reservation entity) { return repository.save(entity); }
    @Override @Transactional(readOnly = true) public List<Reservation> findAll() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Reservation findById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Reservation introuvable : " + id)); }
    @Override public Reservation update(Long id, Reservation entity) {
        Reservation existing = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Reservation introuvable : " + id));
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setStatut(entity.getStatut());
        existing.setVehicule(entity.getVehicule());
        existing.setClient(entity.getClient());
        return repository.save(existing);
    }
    @Override public void delete(Long id) {
        if (!repository.existsById(id)) throw new NoSuchElementException("Reservation introuvable : " + id);
        repository.deleteById(id);
    }
}