package app.dto;

import app.entities.Destination;
import app.entities.Flight;
import app.enums.FlightStatus;
import app.services.interfaces.AircraftService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FlightDTO {
    private Long id;
    @NotBlank(message = "Code cannot be empty")
    @Size(min = 2, max = 15, message = "Length of Flight code should be between 2 and 15 characters")
    private String code;
    private Destination from;
    private Destination to;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private Long aircraftID;
    private FlightStatus flightStatus;

    public static AircraftService aircraftService;

    public FlightDTO(Flight flight) {
        this.id = flight.getId();
        this.code = flight.getCode();
        this.from = flight.getFrom();
        this.to = flight.getTo();
        this.departureDateTime = flight.getDepartureDateTime();
        this.arrivalDateTime = flight.getArrivalDateTime();
        this.aircraftID = flight.getId();
        this.flightStatus = flight.getFlightStatus();
    }

    public static Flight convertToFlightEntity(FlightDTO flightDTO) {
        Flight flight = new Flight();
        flight.setId(flightDTO.getId());
        flight.setCode(flight.getCode());
        flight.setFrom(flightDTO.getFrom());
        flight.setTo(flightDTO.getTo());
        flight.setDepartureDateTime(flightDTO.getDepartureDateTime());
        flight.setArrivalDateTime(flight.getArrivalDateTime());
        flight.setAircraft(aircraftService.findById(flightDTO.getId()));
        flight.setFlightStatus(flightDTO.getFlightStatus());
        return flight;
    }

}
