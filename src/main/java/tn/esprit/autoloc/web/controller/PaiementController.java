package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.service.IPaiementService;
import tn.esprit.autoloc.web.dto.PaiementRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "AutoLoc paiements API")
public class PaiementController {
    private final IPaiementService service;
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create paiement")
    @ApiResponse(responseCode = "201", description = "Resource created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Paiement create(@RequestBody PaiementRequest request) { return service.create(request.toEntity()); }

    @GetMapping
    @Operation(summary = "List paiement resources")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public List<Paiement> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Get paiement by ID")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public Paiement findById(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    @Operation(summary = "Update paiement")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Paiement update(@PathVariable Long id, @RequestBody PaiementRequest request) {
        return service.update(id, request.toEntity());
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete paiement")
    @ApiResponse(responseCode = "204", description = "Resource deleted")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public void delete(@PathVariable Long id) { service.deleteById(id); }
}
