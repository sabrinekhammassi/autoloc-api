package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientRequest;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "AutoLoc clients API")
public class ClientController {
    private final IClientService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create client")
    @ApiResponse(responseCode = "201", description = "Resource created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Client create(@RequestBody ClientRequest request) {
        return service.create(request.toEntity());
    }

    @GetMapping
    @Operation(summary = "List client resources")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public List<Client> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get client by ID")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public Client findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Client update(@PathVariable Long id, @RequestBody ClientRequest request) {
        return service.update(id, request.toEntity());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete client")
    @ApiResponse(responseCode = "204", description = "Resource deleted")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
