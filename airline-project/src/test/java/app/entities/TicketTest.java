package app.entities;

import app.dto.TicketDTO;
import app.entities.account.Passenger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

public class TicketTest extends EntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void nullTicketNumberFieldShouldNotValidate() {
        Ticket ticket = new Ticket(1L, null, new Passenger(), new Flight(), new FlightSeat());
        TicketDTO ticketDTO = new TicketDTO(ticket);
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, ticketDTO));
    }

    @Test
    public void notNullTicketNumberFieldShouldValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), new Flight(), new FlightSeat());
        TicketDTO ticketDTO = new TicketDTO(ticket);
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, ticketDTO));
    }

    @Test
    public void nullFlightFieldShouldNotValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), null, new FlightSeat());
        TicketDTO ticketDTO = new TicketDTO(ticket);
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, ticketDTO));
    }

    @Test
    public void notNullFlightFieldShouldValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), new Flight(), new FlightSeat());
        TicketDTO ticketDTO = new TicketDTO(ticket);
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, ticketDTO));
    }

    @Test
    public void nullFlightSeatFieldShouldNotValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), new Flight(), null);
        TicketDTO ticketDTO = new TicketDTO(ticket);
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, ticketDTO));
    }

    @Test
    public void notNullFlightSeatFieldsShouldValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), new Flight(), new FlightSeat());
        TicketDTO ticketDTO = new TicketDTO(ticket);
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, ticketDTO));
    }

}
