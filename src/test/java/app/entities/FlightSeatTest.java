package app.entities;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;

public class FlightSeatTest extends EntityTest {

    private Validator validator;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        mapper = new ObjectMapper();
    }

    private JSONObject initJSONObject() {
        JSONObject flightSeatJson = new JSONObject();

        flightSeatJson.put("id", 1L);
        flightSeatJson.put("fare", 1500);
        flightSeatJson.put("isRegistered", true);
        flightSeatJson.put("isSold", true);
        flightSeatJson.put("isBooking", true);
        flightSeatJson.put("flight", new JSONObject());
        flightSeatJson.put("seat", new JSONObject());

        return flightSeatJson;
    }


    @Test
    public void validFlightSeatShouldValidate() {
        FlightSeat testFlightSeat;
        JSONObject flightSeatJson = initJSONObject();

        try {
            testFlightSeat = mapper.readValue(flightSeatJson.toString(), FlightSeat.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, testFlightSeat));
    }

    @Test
    public void negativeFareShouldNotValidate() {
        FlightSeat testFlightSeat;
        JSONObject flightSeatJson = initJSONObject();
        flightSeatJson.replace("fare", -100);
        try {
            testFlightSeat = mapper.readValue(flightSeatJson.toString(), FlightSeat.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testFlightSeat));
    }

    @Test
    public void nullIsRegisteredShouldNotValidate() {
        FlightSeat testFlightSeat;
        JSONObject flightSeatJson = initJSONObject();
        flightSeatJson.replace("isRegistered", null);
        try {
            testFlightSeat = mapper.readValue(flightSeatJson.toString(), FlightSeat.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testFlightSeat));
    }
    @Test
    public void nullIsSoldShouldNotValidate() {
        FlightSeat testFlightSeat;
        JSONObject flightSeatJson = initJSONObject();
        flightSeatJson.replace("isSold", null);
        try {
            testFlightSeat = mapper.readValue(flightSeatJson.toString(), FlightSeat.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testFlightSeat));
    }

    @Test
    public void nullFlightShouldNotValidate() {
        FlightSeat testFlightSeat;
        JSONObject flightSeatJson = initJSONObject();
        flightSeatJson.replace("flight", null);
        try {
            testFlightSeat = mapper.readValue(flightSeatJson.toString(), FlightSeat.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testFlightSeat));
    }

    @Test
    public void nullSeatShouldNotValidate() {
        FlightSeat testFlightSeat;
        JSONObject flightSeatJson = initJSONObject();
        flightSeatJson.replace("seat", null);
        try {
            testFlightSeat = mapper.readValue(flightSeatJson.toString(), FlightSeat.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testFlightSeat));
    }
}
