package app.services;

import app.entities.Destination;
import app.enums.Airport;
import app.repositories.DestinationRepository;
import app.services.interfaces.DestinationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DestinationServiceImpl implements DestinationService {
    private final DestinationRepository destinationRepository;

    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public List<Destination> findAllDestinations() {
        return destinationRepository.findAll();
    }

    @Override
    public List<Destination> findDestinationByName(String cityName, String countryName) {
        if (cityName != null) {
            return destinationRepository.findByCityNameContainingIgnoreCase(cityName);
        } else {
            return destinationRepository.findByCountryNameContainingIgnoreCase(countryName);
        }
    }

    @Override
    @Transactional
    public void saveDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    @Override
    @Transactional
    public void updateDestination(Long id, Destination destination) {
        destination.setId(id);
        destinationRepository.save(destination);
    }

    @Override
    @Transactional
    public void deleteDestinationById(Long id) {
        destinationRepository.deleteById(id);
    }

    @Override
    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }

    @Override
    public Destination findDestinationByAirportCode(Airport airportCode) {
        return destinationRepository.findDestinationByAirportCode(airportCode).orElse(null);
    }
}
