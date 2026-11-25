package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IContratService;
import tn.esprit.autoloc.web.dto.ContratRequest;
import tn.esprit.autoloc.web.dto.ContratResponse;
import tn.esprit.autoloc.web.mapper.ContratMapper;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats", description = "AutoLoc contrats API")
public class ContratController {
    private final IContratService service;
    private final ContratMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create contrat")
    public ContratResponse create(@Valid @RequestBody ContratRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List contrat resources")
    public List<ContratResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get contrat by ID")
    public ContratResponse findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update contrat")
    public ContratResponse update(@PathVariable Long id, @Valid @RequestBody ContratRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete contrat")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
