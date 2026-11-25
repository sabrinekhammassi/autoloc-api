package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientRequest;
import tn.esprit.autoloc.web.dto.ClientResponse;
import tn.esprit.autoloc.web.mapper.ClientMapper;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "AutoLoc clients API")
public class ClientController {
    private final IClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create client")
    public ClientResponse create(@Valid @RequestBody ClientRequest request) {
        return mapper.toResponse(service.create(mapper.toEntity(request)));
    }

    @GetMapping
    @Operation(summary = "List client resources")
    public List<ClientResponse> findAll() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID")
    public ClientResponse findById(@PathVariable Long id) {
        return mapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client")
    public ClientResponse update(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        return mapper.toResponse(service.update(id, mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete client")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
