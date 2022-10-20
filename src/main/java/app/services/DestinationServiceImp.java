package app.services;

import app.entities.Destination;
import app.repositories.DestinationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DestinationServiceImp implements DestinationService {
    private final DestinationRepository destinationRepository;

    public DestinationServiceImp(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
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
    public void updateDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    @Override
    @Transactional
    public void deleteDestinationById(Long id) {
        destinationRepository.deleteById(id);
    }
}
