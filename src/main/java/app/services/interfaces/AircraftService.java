package app.services.interfaces;

import app.entities.Aircraft;

import java.util.List;

public interface AircraftService {

    Aircraft save(Aircraft aircraft);
    List<Aircraft> findAll();
    Aircraft findById(Long id);
    Aircraft findByAircraftNumber(String aircraftNumber);
    void delete(Long id);
}
