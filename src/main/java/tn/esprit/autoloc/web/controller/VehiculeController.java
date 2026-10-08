package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;
import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
public class VehiculeController {
    private final IVehiculeService service;
    @PostMapping public Vehicule create(@RequestBody Vehicule entity) { return service.create(entity); }
    @GetMapping public List<Vehicule> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Vehicule findById(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Vehicule update(@PathVariable Long id, @RequestBody Vehicule entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.delete(id); }
}