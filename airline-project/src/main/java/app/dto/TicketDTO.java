package app.dto;

import app.entities.Flight;
import app.entities.FlightSeat;
import app.entities.Ticket;
import app.entities.account.Passenger;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TicketDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    private String ticketNumber;

    private Passenger passenger;

    @NotNull
    private Flight flight;

    @NotNull
    private FlightSeat flightSeat;

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.ticketNumber = ticket.getTicketNumber();
        this.passenger = ticket.getPassenger();
        this.flight = ticket.getFlight();
        this.flightSeat = ticket.getFlightSeat();
    }
}
