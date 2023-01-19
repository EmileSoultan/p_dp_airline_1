package app.entities;

import app.entities.user.Passenger;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class TicketTest extends EntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void nullTicketNumberFieldShouldNotValidate() {
        Ticket ticket = new Ticket(1L, null, new Passenger(), new Flight(), new FlightSeat());
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, ticket));
    }

    @Test
    public void notNullTicketNumberFieldShouldValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), new Flight(), new FlightSeat());
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, ticket));
    }

    @Test
    public void nullFlightFieldShouldNotValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), null, new FlightSeat());
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, ticket));
    }

    @Test
    public void notNullFlightFieldShouldValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), new Flight(), new FlightSeat());
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, ticket));
    }

    @Test
    public void nullFlightSeatFieldShouldNotValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), new Flight(), null);
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, ticket));
    }

    @Test
    public void notNullFlightSeatFieldsShouldValidate() {
        Ticket ticket = new Ticket(1L, "ticketnumber", new Passenger(), new Flight(), new FlightSeat());
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, ticket));
    }

}
