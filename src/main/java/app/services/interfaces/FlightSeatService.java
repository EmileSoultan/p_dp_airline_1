package app.services.interfaces;

import app.entities.Flight;
import app.entities.FlightSeat;
import app.entities.Seat;

import java.util.Set;

public interface FlightSeatService {

    Set<FlightSeat> findAll();

    FlightSeat findById(Long id);

    Set<FlightSeat> findByFlightNumber(String flightNumber);

    Set<FlightSeat> addFlightSeatsByFlightNumber(String flightNumber);

    FlightSeat saveFlightSeat(FlightSeat flightSeat);
    int getNumberOfFreeSeatOnFlight(Flight flight);
    Set<Seat> getSetOfFeeSeatOnFlightByFlightId(Long id);

}
