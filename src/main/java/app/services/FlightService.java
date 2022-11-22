package app.services;

import app.entities.Destination;
import app.entities.Flight;
import app.entities.FlightSeat;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FlightService {

    List<Flight> getAllFlights();
    Set<FlightSeat> getFreeSeats(Long id);
    Flight getFlightByCode(String code);
    List<Flight> getFlightByDestinationsAndDates(String from, String to, String start, String finish);
    List<Flight> getFlightsByDestinationsAndDepartureDate(Destination fromId, Destination toId, LocalDate departureDate);
    Flight getFlightByIdAndDates(Long id, String start, String finish);
    Flight getById(Long id);
    void save(Flight flight);
    void update(Flight updated);
}
