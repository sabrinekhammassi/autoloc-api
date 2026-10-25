package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.web.dto.AgenceRequest;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "AutoLoc agences API")
public class AgenceController {
    private final IAgenceService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create agence")
    @ApiResponse(responseCode = "201", description = "Resource created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Agence create(@RequestBody AgenceRequest request) {
        return service.create(request.toEntity());
    }

    @GetMapping
    @Operation(summary = "List agence resources")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public List<Agence> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get agence by ID")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public Agence findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update agence")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Agence update(@PathVariable Long id, @RequestBody AgenceRequest request) {
        return service.update(id, request.toEntity());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete agence")
    @ApiResponse(responseCode = "204", description = "Resource deleted")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
