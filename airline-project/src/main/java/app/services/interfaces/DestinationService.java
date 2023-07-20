package app.services.interfaces;

import app.entities.Destination;
import app.enums.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface DestinationService {

    Page<Destination> findAll(Pageable pageable);

    Page<Destination> findDestinationByNameAndTimezone(Pageable pageable, String cityName, String countryName, String timezone);

    void saveDestination(Destination destination);

    void updateDestination(Long id, Destination destination);

    Destination getDestinationById(Long id);

    Destination findDestinationByAirportCode(Airport airportCode);

    void deleteById(Long id);
}
