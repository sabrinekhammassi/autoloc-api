package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {
    private final IClientRepository repository;

    @Override
    public Client create(Client entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Client est obligatoire");
        }
        if (entity.getIdClient() != null) {
            throw new IllegalArgumentException("Un nouvel objet Client ne doit pas avoir d'identifiant");
        }
        validate(entity);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Client update(Long id, Client entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Client est obligatoire");
        }
        Client existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", id));
        validate(entity);
        existing.setNom(entity.getNom());
        existing.setPrenom(entity.getPrenom());
        existing.setEmail(entity.getEmail());
        existing.setTelephone(entity.getTelephone());
        existing.setNumPermis(entity.getNumPermis());
        existing.setDateInscription(entity.getDateInscription());
        return repository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Client", id);
        }
        repository.deleteById(id);
    }

    private void validate(Client entity) {
        notBlank(entity.getNom(), "nom");
        notBlank(entity.getPrenom(), "prenom");
        notBlank(entity.getEmail(), "email");
        notBlank(entity.getNumPermis(), "numPermis");
        required(entity.getDateInscription(), "dateInscription");
    }

    private static void notBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }

    private static void required(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " est obligatoire");
        }
    }
}
