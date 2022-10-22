package app.services;

import app.entities.Aircraft;
import app.repositories.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AircraftService {

    private AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Transactional
    public Aircraft save(Aircraft aircraft) {
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
        aircraftRepository.deleteById(id);
    }

}
