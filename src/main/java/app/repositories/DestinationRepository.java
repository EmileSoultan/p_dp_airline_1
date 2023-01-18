package app.repositories;

import app.entities.Destination;
import app.enums.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByCityNameContainingIgnoreCase(String name);

    List<Destination> findByCountryNameContainingIgnoreCase(String name);
    Destination getDestinationByAirportCode(Airport airportCode);

    Optional <Destination> findDestinationByAirportCode(Airport airportCode);
}
