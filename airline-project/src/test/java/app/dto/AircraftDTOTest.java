package app.dto;

import app.entities.EntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class AircraftDTOTest extends EntityTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void blankAircraftNumberFieldShouldNotValidate() {
        AircraftDTO aircraft = new AircraftDTO();
        aircraft.setId(1L);
        aircraft.setAircraftNumber("");
        aircraft.setModel("boeing354A");
        aircraft.setModelYear(2054);
        aircraft.setFlightRange(250);


        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, aircraft));
    }

    @Test
    public void notBlankAircraftNumberFieldShouldValidate() {
        AircraftDTO aircraft = new AircraftDTO();
        aircraft.setId(1L);
        aircraft.setAircraftNumber("435HA");
        aircraft.setModel("boeing354A");
        aircraft.setModelYear(2054);
        aircraft.setFlightRange(250);

        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, aircraft));
    }

    @Test
    public void blankModelFieldShouldNotValidate() {
        AircraftDTO aircraft = new AircraftDTO();
        aircraft.setId(1L);
        aircraft.setAircraftNumber("435HA");
        aircraft.setModel("");
        aircraft.setModelYear(2054);
        aircraft.setFlightRange(250);

        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, aircraft));
    }

    @Test
    public void notBlankModelFieldShouldValidate() {
        AircraftDTO aircraft = new AircraftDTO();
        aircraft.setId(1L);
        aircraft.setAircraftNumber("435HA");
        aircraft.setModel("boeing345");
        aircraft.setModelYear(2054);
        aircraft.setFlightRange(250);
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, aircraft));
    }

    @Test
    public void lessThanMinModelYearFieldShouldNotValidate() {
        AircraftDTO aircraft = new AircraftDTO();
        aircraft.setId(1L);
        aircraft.setAircraftNumber("435HA");
        aircraft.setModel("boeing435");
        aircraft.setModelYear(2);
        aircraft.setFlightRange(250);
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, aircraft));
    }

    @Test
    public void moreThanMinModelYearFieldShouldNotValidate() {
        AircraftDTO aircraft = new AircraftDTO();
        aircraft.setId(1L);
        aircraft.setAircraftNumber("435HA");
        aircraft.setModel("boeing435");
        aircraft.setModelYear(2005);
        aircraft.setFlightRange(250);
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, aircraft));
    }

    @Test
    public void nullFlightRangeFieldShouldNotValidate() {
        AircraftDTO aircraft = new AircraftDTO();
        aircraft.setId(1L);
        aircraft.setAircraftNumber("435HA");
        aircraft.setModel("boeing435");
        aircraft.setModelYear(2005);

        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, aircraft));
    }

    @Test
    public void notNullFlightRangeFieldShouldValidate() {
        AircraftDTO aircraft = new AircraftDTO();
        aircraft.setId(1L);
        aircraft.setAircraftNumber("435HA");
        aircraft.setModel("boeing435");
        aircraft.setModelYear(2005);
        aircraft.setFlightRange(200);

        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, aircraft));
    }
}
