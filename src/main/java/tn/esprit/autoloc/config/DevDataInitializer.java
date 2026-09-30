package tn.esprit.autoloc.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.VehiculeRepository;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataInitializer implements CommandLineRunner {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public void run(String... args) {
        List<Vehicule> vehiculesDemo = List.of(
                new Vehicule(null, "DEMO-001", "Toyota", "Yaris", CategorieVehicule.CITADINE,
                        new BigDecimal("120.00"), StatutVehicule.DISPONIBLE),
                new Vehicule(null, "DEMO-002", "Peugeot", "308", CategorieVehicule.BERLINE,
                        new BigDecimal("180.00"), StatutVehicule.DISPONIBLE),
                new Vehicule(null, "DEMO-003", "Hyundai", "Tucson", CategorieVehicule.SUV,
                        new BigDecimal("250.00"), StatutVehicule.MAINTENANCE)
        );

        vehiculesDemo.stream()
                .filter(vehicule -> !vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation()))
                .forEach(vehiculeRepository::save);
    }
}
