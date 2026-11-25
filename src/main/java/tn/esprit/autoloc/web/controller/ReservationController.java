package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationRequest;
import tn.esprit.autoloc.web.dto.ReservationResponseDTO;
import tn.esprit.autoloc.web.dto.ReservationWithContratRequestDTO;
import tn.esprit.autoloc.web.mapper.ReservationMapper;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "AutoLoc reservations API")
public class ReservationController {
    private final IReservationService service;
    private final ReservationMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create reservation")
    public ReservationResponseDTO create(@Valid @RequestBody ReservationRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List reservation resources")
    public List<ReservationResponseDTO> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by ID")
    public ReservationResponseDTO findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reservation")
    public ReservationResponseDTO update(@PathVariable Long id, @Valid @RequestBody ReservationRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @PutMapping("/{idReservation}/client")
    @Operation(summary = "Affecter une réservation à un client")
    public ReservationResponseDTO affecterReservationAClient(
            @PathVariable Long idReservation,
            @RequestParam String nom,
            @RequestParam String prenom) {
        return service.affecterReservationAClient(idReservation, nom, prenom);
    }

    @PostMapping("/avec-contrat")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer une réservation avec son contrat associé")
    public ReservationResponseDTO creerReservationEtContratAssocie(
            @Valid @RequestBody ReservationWithContratRequestDTO requestDTO) {
        return service.creerReservationEtContratAssocie(requestDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete reservation")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
