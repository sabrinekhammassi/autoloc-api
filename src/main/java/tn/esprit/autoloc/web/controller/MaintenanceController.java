package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {
    private final IMaintenanceService service;
    @PostMapping public Maintenance create(@RequestBody Maintenance entity) { return service.create(entity); }
    @GetMapping public List<Maintenance> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Maintenance findById(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Maintenance update(@PathVariable Long id, @RequestBody Maintenance entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
