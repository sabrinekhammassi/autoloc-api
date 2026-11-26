package tn.esprit.autoloc.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContratSchedulerService {
    private static final ZoneId SCHEDULER_ZONE = ZoneId.of("Africa/Lagos");

    private final IContratRepository contratRepository;

    @Scheduled(cron = "0 0 7 * * *", zone = "Africa/Lagos")
    @Transactional(readOnly = true)
    public void identifierContratsArrivantAEcheance() {
        LocalDate aujourdHui = LocalDate.now(SCHEDULER_ZONE);
        LocalDate limite = aujourdHui.plusDays(2);
        List<Contrat> contrats = contratRepository
                .findAllByValideTrueAndReservation_DateFinBetween(aujourdHui, limite);

        if (contrats.isEmpty()) {
            log.info("Aucun contrat valide n'arrive à échéance sous 48 heures.");
            return;
        }

        contrats.forEach(contrat -> log.info(
                "Contrat {} arrive à échéance le {} (réservation {}).",
                contrat.getIdContrat(),
                contrat.getReservation().getDateFin(),
                contrat.getReservation().getIdReservation()));
    }
}
