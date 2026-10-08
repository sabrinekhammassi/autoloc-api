package tn.esprit.autoloc.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataInitializer implements CommandLineRunner {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;

    @Override
    public void run(String... args) {
        Agence agence = agenceRepository.findFirstByNom("Agence Démo")
                .orElseGet(() -> creerAgenceDemo());

        List<Vehicule> vehiculesDemo = List.of(
                creerVehicule("DEMO-001", "Toyota", "Yaris", CategorieVehicule.CITADINE,
                        new BigDecimal("120.00"), StatutVehicule.DISPONIBLE, agence),
                creerVehicule("DEMO-002", "Peugeot", "308", CategorieVehicule.BERLINE,
                        new BigDecimal("180.00"), StatutVehicule.DISPONIBLE, agence),
                creerVehicule("DEMO-003", "Hyundai", "Tucson", CategorieVehicule.SUV,
                        new BigDecimal("250.00"), StatutVehicule.MAINTENANCE, agence)
        );

        vehiculesDemo.stream()
                .filter(vehicule -> !vehiculeRepository.existsByImmatriculation(vehicule.getImmatriculation()))
                .forEach(vehiculeRepository::save);
    }

    private Agence creerAgenceDemo() {
        Agence agence = new Agence();
        agence.setNom("Agence Démo");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("71000000");
        return agenceRepository.save(agence);
    }

    private Vehicule creerVehicule(String immatriculation, String marque, String modele,
                                   CategorieVehicule categorie, BigDecimal tarifJournalier,
                                   StatutVehicule statut, Agence agence) {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation(immatriculation);
        vehicule.setMarque(marque);
        vehicule.setModele(modele);
        vehicule.setCategorie(categorie);
        vehicule.setTarifJournalier(tarifJournalier);
        vehicule.setStatut(statut);
        vehicule.setAgence(agence);
        return vehicule;
    }
}
