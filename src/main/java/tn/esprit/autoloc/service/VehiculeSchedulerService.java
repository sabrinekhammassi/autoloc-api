package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IReservationRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehiculeSchedulerService {
    private static final ZoneId SCHEDULER_ZONE = ZoneId.of("Africa/Lagos");

    private final IReservationRepository reservationRepository;
    private final IVehiculeRepository vehiculeRepository;

    @Scheduled(cron = "0 0 1 * * *", zone = "Africa/Lagos")
    @Transactional
    public void terminerLocationsEtLibererVehicules() {
        LocalDate aujourdHui = LocalDate.now(SCHEDULER_ZONE);
        List<Reservation> reservationsTerminees = reservationRepository
                .findAllByStatutAndDateFinBefore(StatutReservation.CONFIRMEE, aujourdHui);

        for (Reservation reservation : reservationsTerminees) {
            reservation.setStatut(StatutReservation.TERMINEE);
            Vehicule vehicule = reservation.getVehicule();
            vehicule.setStatut(StatutVehicule.DISPONIBLE);
            log.info("Location {} terminée; véhicule {} remis à DISPONIBLE.",
                    reservation.getIdReservation(), vehicule.getIdVehicule());
        }

        reservationRepository.saveAll(reservationsTerminees);
        List<Vehicule> vehicules = reservationsTerminees.stream()
                .map(Reservation::getVehicule)
                .distinct()
                .toList();
        vehiculeRepository.saveAll(vehicules);
    }
}
