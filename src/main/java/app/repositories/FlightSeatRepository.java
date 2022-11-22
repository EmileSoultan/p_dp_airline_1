package app.repositories;

import app.entities.Flight;
import app.entities.FlightSeat;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface FlightSeatRepository extends CrudRepository<FlightSeat, Long> {

    Set<FlightSeat> findFlightSeatByFlight(Flight flight);

}
