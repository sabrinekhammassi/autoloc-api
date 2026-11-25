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
import tn.esprit.autoloc.web.dto.ReservationResponse;
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
    public ReservationResponse create(@Valid @RequestBody ReservationRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List reservation resources")
    public List<ReservationResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by ID")
    public ReservationResponse findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reservation")
    public ReservationResponse update(@PathVariable Long id, @Valid @RequestBody ReservationRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete reservation")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
