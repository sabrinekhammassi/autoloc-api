package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final IClientService service;
    @PostMapping public Client create(@RequestBody Client entity) { return service.create(entity); }
    @GetMapping public List<Client> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Client findById(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Client update(@PathVariable Long id, @RequestBody Client entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
