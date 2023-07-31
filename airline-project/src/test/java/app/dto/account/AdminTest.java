package app.dto.account;

import app.dto.account.AdminDTO;
import app.entities.EntityTest;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;

class  AdminTest extends EntityTest {
    private Validator validator;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        mapper = new ObjectMapper();
    }

    private JSONObject initValidableJSONObject() {
        var validableAdminJson = new JSONObject();

        validableAdminJson.put("@type", "admin");
        validableAdminJson.put("id", 1002L);
        validableAdminJson.put("email", "admin@mail.ru");
        validableAdminJson.put("password", "1Admin@Admin");
        validableAdminJson.put("securityQuestion", "someQuestion");
        validableAdminJson.put("answerQuestion", "someAnswer");
        validableAdminJson.put("roles", null);

        return validableAdminJson;
    }

    @Test
    void validAdminShouldValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    void blankEmailShouldNotValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("email", "");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    void blankPasswordShouldNotValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    void passwordUnder8CharShouldNotValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "1Admin@");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    void passwordWithoutUpperCaseCharShouldNotValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "1admin@admin");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    void passwordWithoutLowerCharShouldNotValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "ADMIN@ADMIN");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    void passwordWithoutNumberShouldNotValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "@Password");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    void passwordWithoutSpecialCharShouldNotValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "1Password");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    void blankQuestionShouldNotValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("securityQuestion", "");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    void blankAnswerShouldNotValidate() {
        AdminDTO testAdmin;
        var adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("answerQuestion", "");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), AdminDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }
}
