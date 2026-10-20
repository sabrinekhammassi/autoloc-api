package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final IReservationService service;
    @PostMapping public Reservation create(@RequestBody Reservation entity) { return service.create(entity); }
    @GetMapping public List<Reservation> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public Reservation findById(@PathVariable Long id) { return service.findById(id); }
    @PutMapping("/{id}") public Reservation update(@PathVariable Long id, @RequestBody Reservation entity) { return service.update(id, entity); }
    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { service.deleteById(id); }
}
