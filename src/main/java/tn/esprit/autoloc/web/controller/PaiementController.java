package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IPaiementService;
import tn.esprit.autoloc.web.dto.PaiementRequest;
import tn.esprit.autoloc.web.dto.PaiementResponse;
import tn.esprit.autoloc.web.mapper.PaiementMapper;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "AutoLoc paiements API")
public class PaiementController {
    private final IPaiementService service;
    private final PaiementMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create paiement")
    public PaiementResponse create(@Valid @RequestBody PaiementRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List paiement resources")
    public List<PaiementResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get paiement by ID")
    public PaiementResponse findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update paiement")
    public PaiementResponse update(@PathVariable Long id, @Valid @RequestBody PaiementRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete paiement")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
