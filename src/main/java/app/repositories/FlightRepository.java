package app.repositories;

import app.entities.Destination;
import app.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    Flight getByCode(String code);

    default List<Flight> getByFromAndToAndDepartureDate(Destination from, Destination to, LocalDate departureDate) {
        return findByFromAndToAndDepartureDateTimeBetween(from, to,
                departureDate.atStartOfDay(), departureDate.plusDays(1).atStartOfDay());
    }
    List<Flight> findByFromAndToAndDepartureDateTimeBetween(Destination from, Destination to,
                                                            LocalDateTime fromDate, LocalDateTime toDate);

    Set<Flight> findByAircraft_Id(Long id);

}
