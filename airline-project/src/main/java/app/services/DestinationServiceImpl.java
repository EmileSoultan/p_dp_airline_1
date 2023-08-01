package app.services;

import app.entities.Destination;
import app.enums.Airport;
import app.repositories.DestinationRepository;
import app.services.interfaces.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    private final DestinationRepository destinationRepository;

    @Override
    public Page<Destination> findAll(Integer page, Integer size) {
        return destinationRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Destination> findDestinationByNameAndTimezone(Pageable pageable, String cityName, String countryName, String timezone) {
        if (cityName != null) {
            return destinationRepository.findByCityNameContainingIgnoreCase(pageable, cityName);
        } else if(countryName != null) {
            return destinationRepository.findByCountryNameContainingIgnoreCase(pageable, countryName);
        } else {
            return destinationRepository.findByTimezoneContainingIgnoreCase(pageable, timezone);
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
    public Destination getDestinationById(Long id) {
        return destinationRepository.findById(id).orElse(null);
    }

    @Override
    public Destination findDestinationByAirportCode(Airport airportCode) {
        return destinationRepository.findDestinationByAirportCode(airportCode).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        destinationRepository.deleteById(id);
    }
}
