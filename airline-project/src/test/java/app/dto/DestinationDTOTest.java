package app.dto;

import app.entities.EntityTest;
import app.enums.Airport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

public class DestinationDTOTest extends EntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void lessThan3CharAirportNameSizeShouldNotValidate() {
        var testDestination = new DestinationDTO(1L, Airport.AER, "Ad", "Adler", "UTC + 3", "RUSSIA");

        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testDestination));
    }

    @Test
    public void between3And15CharAirportNameSizeShouldValidate() {
        var testDestination = new DestinationDTO(1L, Airport.AER, "Adler", "Adler", "UTC + 3", "RUSSIA");

        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, testDestination));
    }

    @Test
    public void moreThan15CharAirportNameSizeShouldNotValidate() {
        var testDestination = new DestinationDTO(1L, Airport.AER, "AdlerAdlerAdlerAdler", "Adler", "UTC + 3", "RUSSIA");

        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testDestination));
    }

    @Test
    public void lessThan3CharCityNameSizeShouldNotValidate() {
        var testDestination = new DestinationDTO(1L, Airport.AER, "Adler", "A", "UTC + 3", "RUSSIA");

        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testDestination));
    }

    @Test
    public void between3And15CharCityNameSizeShouldValidate() {
        var testDestination = new DestinationDTO(1L, Airport.AER, "Adler", "Adler", "UTC + 3", "RUSSIA");

        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, testDestination));
    }

    @Test
    public void moreThan15CharCityNameSizeShouldNotValidate() {
        var testDestination = new DestinationDTO(1L, Airport.AER, "Adler", "GOROD_SKAZKA_GOROD_MECHTA_POPADAESH_V_EGO_SETI", "UTC + 3", "RUSSIA");

        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testDestination));
    }

    @Test
    public void lessThan3CharCountryNameSizeShouldNotValidate() {
        var testDestination = new DestinationDTO(1L, Airport.AER, "Adler", "Adler", "UTC + 3", "RU");

        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testDestination));
    }

    @Test
    public void between3And30CharCountryNameSizeShouldValidate() {
        var testDestination = new DestinationDTO(1L, Airport.AER, "Adler", "Adler", "UTC + 3", "RUSSIA");

        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, testDestination));
    }

    @Test
    public void moreThan30CharCountryNameSizeShouldNotValidate() {
        var testDestination = new DestinationDTO(1L, Airport.AER, "Adler", "Adler", "UTC + 3",
                "RUSSIASHA_RUSSIASHA_RUSSIASHA_RUSSIASHA");

        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testDestination));
    }

}
