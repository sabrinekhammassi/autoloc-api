package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;
import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
public class AgenceController {
    private final IAgenceService service;
    @PostMapping public Agence create(@RequestBody Agence entity) { return service.create(entity); }
    @GetMapping public List<Agence> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Agence findById(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Agence update(@PathVariable Long id, @RequestBody Agence entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}