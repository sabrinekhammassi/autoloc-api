package tn.esprit.autoloc.web.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import tn.esprit.autoloc.service.IReservationService;
import tn.esprit.autoloc.web.dto.ReservationRequest;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "AutoLoc reservations API")
public class ReservationController {
    private final IReservationService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create reservation")
    @ApiResponse(responseCode = "201", description = "Resource created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Reservation create(@RequestBody ReservationRequest request) {
        return service.create(request.toEntity());
    }

    @GetMapping
    @Operation(summary = "List reservation resources")
    @ApiResponse(responseCode = "200", description = "Successful response")
    public List<Reservation> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by ID")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public Reservation findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reservation")
    @ApiResponse(responseCode = "200", description = "Successful response")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public Reservation update(@PathVariable Long id, @RequestBody ReservationRequest request) {
        return service.update(id, request.toEntity());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete reservation")
    @ApiResponse(responseCode = "204", description = "Resource deleted")
    @ApiResponse(responseCode = "404", description = "Resource not found")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
