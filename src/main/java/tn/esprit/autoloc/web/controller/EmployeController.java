package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {
    private final IEmployeService service;
    @PostMapping public Employe create(@RequestBody Employe entity) { return service.create(entity); }
    @GetMapping public List<Employe> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Employe findById(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Employe update(@PathVariable Long id, @RequestBody Employe entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
