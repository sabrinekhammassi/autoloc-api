package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
public class EquipementController {
    private final IEquipementService service;
    @PostMapping public Equipement create(@RequestBody Equipement entity) { return service.create(entity); }
    @GetMapping public List<Equipement> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Equipement findById(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Equipement update(@PathVariable Long id, @RequestBody Equipement entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
