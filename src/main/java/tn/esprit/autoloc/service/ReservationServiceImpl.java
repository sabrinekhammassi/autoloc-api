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
        if (entity.getIdReservation() != null) throw new IllegalArgumentException("Un nouveau Reservation ne doit pas avoir d'identifiant");
        validate(entity);
        return repository.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id)); }
    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() { return repository.findAll(); }
    @Override
    public Reservation update(Long id, Reservation entity) {
        Reservation existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation", id));
        validate(entity);
        existing.setDateDebut(entity.getDateDebut());
        existing.setDateFin(entity.getDateFin());
        existing.setStatut(entity.getStatut());
        existing.setVehicule(entity.getVehicule());
        existing.setClient(entity.getClient());
        return repository.save(existing);
    }
    @Override
    public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Reservation", id); repository.deleteById(id); }
    private void validate(Reservation e) { required(e.getDateDebut(),"dateDebut"); required(e.getDateFin(),"dateFin"); if(e.getDateFin().isBefore(e.getDateDebut())) throw new IllegalArgumentException("dateFin antÃ©rieure Ã  dateDebut"); required(e.getStatut(),"statut"); required(e.getVehicule(),"vehicule"); required(e.getClient(),"client"); }
    private static void required(Object v,String f) { if(v==null) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void blank(String v,String f) { if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void nonNegative(java.math.BigDecimal v,String f) { if(v==null||v.signum()<0) throw new IllegalArgumentException(f+" doit Ãªtre positif ou nul"); }
}

