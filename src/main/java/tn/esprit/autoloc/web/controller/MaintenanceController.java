package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceRequest;
import tn.esprit.autoloc.web.dto.MaintenanceResponse;
import tn.esprit.autoloc.web.mapper.MaintenanceMapper;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "AutoLoc maintenances API")
public class MaintenanceController {
    private final IMaintenanceService service;
    private final MaintenanceMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create maintenance")
    public MaintenanceResponse create(@Valid @RequestBody MaintenanceRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List maintenance resources")
    public List<MaintenanceResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get maintenance by ID")
    public MaintenanceResponse findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update maintenance")
    public MaintenanceResponse update(@PathVariable Long id, @Valid @RequestBody MaintenanceRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete maintenance")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
