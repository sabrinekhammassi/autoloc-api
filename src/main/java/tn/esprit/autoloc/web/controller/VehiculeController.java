package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeRequest;
import tn.esprit.autoloc.web.dto.VehiculeResponse;
import tn.esprit.autoloc.web.mapper.VehiculeMapper;

@RestController
@RequestMapping("/api/vehicules")
@RequiredArgsConstructor
@Tag(name = "Vehicules", description = "AutoLoc vehicules API")
public class VehiculeController {
    private final IVehiculeService service;
    private final VehiculeMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create vehicule")
    public VehiculeResponse create(@Valid @RequestBody VehiculeRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List vehicule resources")
    public List<VehiculeResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get vehicule by ID")
    public VehiculeResponse findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update vehicule")
    public VehiculeResponse update(@PathVariable Long id, @Valid @RequestBody VehiculeRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete vehicule")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
