package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import tn.esprit.autoloc.service.IMaintenanceService;
import tn.esprit.autoloc.web.dto.MaintenanceRequest;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "AutoLoc maintenances API")
public class MaintenanceController {
    private final IMaintenanceService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create maintenance")
    @ApiResponse(responseCode = "201", description = "Resource created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Maintenance create(@RequestBody MaintenanceRequest request) {
        return service.create(request.toEntity());
    }

    @GetMapping
    @Operation(summary = "List maintenance resources")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public List<Maintenance> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get maintenance by ID")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public Maintenance findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update maintenance")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Maintenance update(@PathVariable Long id, @RequestBody MaintenanceRequest request) {
        return service.update(id, request.toEntity());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete maintenance")
    @ApiResponse(responseCode = "204", description = "Resource deleted")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
