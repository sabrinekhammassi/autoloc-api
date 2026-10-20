package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IPaiementRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements IPaiementService {
    private final IPaiementRepository repository;
    @Override
    @Transactional(readOnly = true)
    public Paiement findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paiement", id)); }
    @Override
    @Transactional(readOnly = true)
    public List<Paiement> findAll() { return repository.findAll(); }
    private static void required(Object v,String f) { if(v==null) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void blank(String v,String f) { if(v==null||v.isBlank()) throw new IllegalArgumentException(f+" est obligatoire"); }
    private static void nonNegative(java.math.BigDecimal v,String f) { if(v==null||v.signum()<0) throw new IllegalArgumentException(f+" doit Ãªtre positif ou nul"); }
}

