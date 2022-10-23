package app.services;

import app.entities.Flight;
import java.util.List;
import java.util.Map;

public interface FlightService {

    List<Flight> getAllFlights();
    Map<String, Integer> getFreeSeats(Long id);
    Flight getFlightByCode(String code);
    List<Flight> getFlightByDestinationsAndDates(String from, String to, String start, String finish);
    Flight getFlightByIdAndDates(Long id, String start, String finish);
    Flight getById(Long id);
    void save(Flight flight);
    void update(Flight updated);
}
