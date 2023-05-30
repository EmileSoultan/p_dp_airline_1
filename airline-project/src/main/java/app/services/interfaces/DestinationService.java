package app.services.interfaces;

import app.entities.Destination;
import app.enums.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface DestinationService {

    Page<Destination> findAll(Pageable pageable);

    Page<Destination> findDestinationByName(Pageable pageable, String cityName, String countryName);

    void saveDestination(Destination destination);

    void updateDestination(Long id, Destination destination);

    Destination getDestinationById(Long id);

    Destination findDestinationByAirportCode(Airport airportCode);
}
