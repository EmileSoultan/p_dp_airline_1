package app.util.mappers;

import app.dto.FlightSeatDTO;
import app.entities.FlightSeat;
import app.services.interfaces.FlightService;
import app.services.interfaces.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlightSeatMapper {
    private final SeatService seatService;
    private final FlightService flightService;

    public FlightSeat convertToFlightSeatEntity(FlightSeatDTO dto) {
        FlightSeat flightSeat = new FlightSeat();
        flightSeat.setId(dto.getId());
        flightSeat.setFare(dto.getFare());
        flightSeat.setIsRegistered(dto.getIsRegistered());
        flightSeat.setIsSold(dto.getIsSold());
        flightSeat.setIsBooking(dto.getIsBooking());
        flightSeat.setFlight(flightService.getById(dto.getFlightId()));
        flightSeat.setSeat(seatService.findById(dto.getSeatId()));
        return flightSeat;
    }

    public FlightSeatDTO convertToFlightSeatDTOEntity(FlightSeat flightSeat) {
        FlightSeatDTO flightSeatDTO = new FlightSeatDTO();
        flightSeatDTO.setId(flightSeat.getId());
        flightSeatDTO.setFare(flightSeat.getFare());
        flightSeatDTO.setIsRegistered(flightSeat.getIsRegistered());
        flightSeatDTO.setIsSold(flightSeat.getIsSold());
        flightSeatDTO.setIsBooking(flightSeat.getIsBooking());
        flightSeatDTO.setFlightId(flightService.getById(flightSeat.getFlight().getId()).getId());
        flightSeatDTO.setSeatId(seatService.findById(flightSeat.getSeat().getId()).getId());
        return flightSeatDTO;
    }
}
