package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import tn.esprit.autoloc.domain.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByNomIgnoreCaseAndPrenomIgnoreCase(String nom, String prenom);
}
