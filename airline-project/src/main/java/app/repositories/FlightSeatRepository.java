package app.repositories;

import app.entities.Flight;
import app.entities.FlightSeat;
import app.entities.Seat;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface FlightSeatRepository extends CrudRepository<FlightSeat, Long> {

    Set<FlightSeat> findFlightSeatsByFlightId(Long flightId);

    Set<FlightSeat> findAllFlightsSeatByFlightIdAndIsSoldFalse(Long flightId);

    Set<FlightSeat> findFlightSeatByFlight(Flight flight);

    @Query(value = "select fs2 \n" +
            "from FlightSeat fs2 \n" +
            "join fetch Flight f on f.id = fs2.flight.id \n" +
            "join fetch Seat s on s.id = fs2.seat.id\n" +
            "where f.code = ?1 and s.seatNumber  = ?2")
    Optional<FlightSeat> findFlightSeatByFlightAndSeat(String flightCode, String seatNumber);

    Set<FlightSeat> findFlightSeatsBySeat(Seat seat);
}