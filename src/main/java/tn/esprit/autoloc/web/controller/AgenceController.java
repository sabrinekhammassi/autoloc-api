package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.web.dto.AgenceRequest;
import tn.esprit.autoloc.web.dto.AgenceResponse;
import tn.esprit.autoloc.web.mapper.AgenceMapper;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "AutoLoc agences API")
public class AgenceController {
    private final IAgenceService service;
    private final AgenceMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create agence")
    @ApiResponse(responseCode = "201", description = "Resource created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public AgenceResponse create(@Valid @RequestBody AgenceRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List agence resources")
    public List<AgenceResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get agence by ID")
    public AgenceResponse findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update agence")
    public AgenceResponse update(@PathVariable Long id, @Valid @RequestBody AgenceRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete agence")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
