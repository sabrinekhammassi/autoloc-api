package tn.esprit.autoloc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.service.IClientService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {
    private final IClientRepository repository;
    @Override public Client create(Client entity) { return repository.save(entity); }
    @Override @Transactional(readOnly = true) public List<Client> findAll() { return repository.findAll(); }
    @Override @Transactional(readOnly = true) public Client findById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Client introuvable : " + id)); }
    @Override public Client update(Long id, Client entity) {
        Client existing = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Client introuvable : " + id));
        existing.setNom(entity.getNom());
        existing.setPrenom(entity.getPrenom());
        existing.setEmail(entity.getEmail());
        existing.setTelephone(entity.getTelephone());
        existing.setNumPermis(entity.getNumPermis());
        existing.setDateInscription(entity.getDateInscription());
        return repository.save(existing);
    }
    @Override public void delete(Long id) {
        if (!repository.existsById(id)) throw new NoSuchElementException("Client introuvable : " + id);
        repository.deleteById(id);
    }
}