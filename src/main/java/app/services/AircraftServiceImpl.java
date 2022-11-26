package app.services;

import app.entities.Aircraft;
import app.entities.Flight;
import app.repositories.AircraftRepository;
import app.repositories.FlightRepository;
import app.services.interfaces.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class AircraftServiceImpl implements AircraftService {

    private AircraftRepository aircraftRepository;
    private FlightRepository flightRepository;

    @Autowired
    public AircraftServiceImpl(AircraftRepository aircraftRepository, FlightRepository flightRepository) {
        this.aircraftRepository = aircraftRepository;
        this.flightRepository = flightRepository;
    }

    @Transactional
    public Aircraft save(Aircraft aircraft) {
        if (!aircraft.getSeatSet().isEmpty()) {
            aircraft.getSeatSet().forEach(seat -> seat.setAircraft(aircraft));
        }
        return aircraftRepository.save(aircraft);
    }

    public List<Aircraft> findAll() {
        return aircraftRepository.findAll();
    }

    public Aircraft findById(Long id) {
        return aircraftRepository.findById(id).orElse(null);
    }

    public Aircraft findByAircraftNumber(String aircraftNumber) {
        return aircraftRepository.findByAircraftNumber(aircraftNumber);
    }

    @Transactional
    public void delete(Long id) {
        Set<Flight> flightSet = flightRepository.findByAircraft_Id(id);
        if (flightSet != null) {
            flightSet.forEach(flight -> flight.setAircraft(null));
        }
        aircraftRepository.deleteById(id);
    }

}