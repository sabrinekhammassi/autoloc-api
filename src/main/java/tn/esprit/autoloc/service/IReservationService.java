package tn.esprit.autoloc.service;

import tn.esprit.autoloc.domain.Reservation;
import java.util.List;

public interface IReservationService {
    Reservation create(Reservation entity);
    List<Reservation> findAll();
    Reservation findById(Long id);
    Reservation update(Long id, Reservation entity);
    void delete(Long id);
}