package app.dto;

import app.entities.EntityTest;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;


class FlightDTOTest extends EntityTest {
    private Validator validator;
    private ObjectMapper mapper;


    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        mapper = new ObjectMapper();
    }

    private JSONObject initJSONObject() {
        var flightJSON = new JSONObject();
        flightJSON.put("code", "01C");
        return flightJSON;
    }

    @Test
    void emptyFlightCodeFieldShouldNotValidate() {
        FlightDTO testFlight;
        var flightJSON = initJSONObject();
        flightJSON.replace("code", "");
        try {
            testFlight = mapper.readValue(flightJSON.toString(),
                    FlightDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testFlight));
    }

    @Test
    void lessThan2CharCodeSizeShouldNotValidate() {
        FlightDTO testFlight;
        var flightJSON = initJSONObject();
        flightJSON.replace("code", "1");
        try {
            testFlight = mapper.readValue(flightJSON.toString(),
                    FlightDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testFlight));
    }

    @Test
    void between2And15CodeSizeShouldValidate() {
        FlightDTO testFlight;
        var flightJSON = initJSONObject();
        try {
            testFlight = mapper.readValue(flightJSON.toString(),
                    FlightDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, testFlight));
    }

    @Test
    void moreThan15CharCodeSizeShouldNotValidate() {
        FlightDTO testFlight;
        var flightJSON = initJSONObject();
        flightJSON.replace("code", "123456789101112131415");
        try {
            testFlight = mapper.readValue(flightJSON.toString(),
                    FlightDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Exception during mapping from JSON");
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testFlight));
    }
}
