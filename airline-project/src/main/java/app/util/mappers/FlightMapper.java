package app.util.mappers;

import app.dto.FlightDTO;
import app.entities.Flight;
import app.services.interfaces.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightMapper {

    private final AircraftService aircraftService;

    public Flight convertToFlightEntity(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setId(flightDTO.getId());
        flight.setCode(flightDTO.getCode());
        flight.setFrom(flightDTO.getFrom());
        flight.setTo(flightDTO.getTo());
        flight.setDepartureDateTime(flightDTO.getDepartureDateTime());
        flight.setArrivalDateTime(flightDTO.getArrivalDateTime());
        flight.setAircraft(aircraftService.findById(flightDTO.getAircraftId()));
        flight.setFlightStatus(flightDTO.getFlightStatus());
        return flight;
    }

}
