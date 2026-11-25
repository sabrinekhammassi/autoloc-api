package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import tn.esprit.autoloc.domain.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByDateDebutAndDateFin(LocalDate dateDebut, LocalDate dateFin);
}
