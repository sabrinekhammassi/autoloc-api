package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IClientRepository;
import tn.esprit.autoloc.service.IClientService;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {
    private final IClientRepository repository;
    @Override
    public Client create(Client entity) {
        if (entity.getIdClient() != null) throw new IllegalArgumentException("Un nouveau Client ne doit pas avoir d'identifiant");
        validate(entity);
        return repository.save(entity);
    }
    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id)); }
    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() { return repository.findAll(); }
    @Override
    public Client update(Long id, Client entity) {
        Client existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", id));
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
    public void deleteById(Long id) { if (!repository.existsById(id)) throw new ResourceNotFoundException("Client", id); repository.deleteById(id); }
    private void validate(Client e) { blank(e.getNom(),"nom"); blank(e.getPrenom(),"prenom"); blank(e.getEmail(),"email"); blank(e.getNumPermis(),"numPermis"); required(e.getDateInscription(),"dateInscription"); }
    private static void required(Object v,String f) { if(v==null) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void blank(String v,String f) { if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void nonNegative(java.math.BigDecimal v,String f) { if(v==null||v.signum()<0) throw new IllegalArgumentException(f+" doit être positif ou nul"); }
}
