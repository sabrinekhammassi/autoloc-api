package tn.esprit.autoloc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Agence;

public interface AgenceRepository extends JpaRepository<Agence, Long> {
    Optional<Agence> findFirstByNom(String nom);
}
