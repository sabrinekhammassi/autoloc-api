package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IEquipementService;
import tn.esprit.autoloc.web.dto.EquipementRequest;
import tn.esprit.autoloc.web.dto.EquipementResponse;
import tn.esprit.autoloc.web.mapper.EquipementMapper;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
@Tag(name = "Equipements", description = "AutoLoc equipements API")
public class EquipementController {
    private final IEquipementService service;
    private final EquipementMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create equipement")
    public EquipementResponse create(@Valid @RequestBody EquipementRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List equipement resources")
    public List<EquipementResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get equipement by ID")
    public EquipementResponse findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update equipement")
    public EquipementResponse update(@PathVariable Long id, @Valid @RequestBody EquipementRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete equipement")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
