package app.services.interfaces;

import app.entities.Destination;
import app.enums.Airport;
import org.springframework.data.domain.Page;



public interface DestinationService {

    Page<Destination> findAll(Integer page, Integer size);

    Page<Destination> findDestinationByNameAndTimezone(Integer page, Integer size, String cityName, String countryName, String timezone);

    void saveDestination(Destination destination);

    void updateDestination(Long id, Destination destination);

    Destination getDestinationById(Long id);

    Destination findDestinationByAirportCode(Airport airportCode);

    void deleteById(Long id);
}
