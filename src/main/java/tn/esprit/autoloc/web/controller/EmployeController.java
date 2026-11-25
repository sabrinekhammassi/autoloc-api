package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IEmployeService;
import tn.esprit.autoloc.web.dto.EmployeRequest;
import tn.esprit.autoloc.web.dto.EmployeResponse;
import tn.esprit.autoloc.web.mapper.EmployeMapper;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
@Tag(name = "Employes", description = "AutoLoc employes API")
public class EmployeController {
    private final IEmployeService service;
    private final EmployeMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create employe")
    public EmployeResponse create(@Valid @RequestBody EmployeRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List employe resources")
    public List<EmployeResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employe by ID")
    public EmployeResponse findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employe")
    public EmployeResponse update(@PathVariable Long id, @Valid @RequestBody EmployeRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete employe")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
