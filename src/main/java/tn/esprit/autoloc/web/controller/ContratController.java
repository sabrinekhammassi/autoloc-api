package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {
    private final IContratService service;
    @PostMapping public Contrat create(@RequestBody Contrat entity) { return service.create(entity); }
    @GetMapping public List<Contrat> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Contrat findById(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Contrat update(@PathVariable Long id, @RequestBody Contrat entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
