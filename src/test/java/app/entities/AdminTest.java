package app.entities;

import app.entities.user.Admin;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;

public class AdminTest extends EntityTest {
    private Validator validator;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        mapper = new ObjectMapper();
    }

    private JSONObject initValidableJSONObject() {
        JSONObject validableAdminJson = new JSONObject();

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
    public void validAdminShouldValidate() {
        Admin testAdmin;
        JSONObject adminJsonObject = initValidableJSONObject();

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), Admin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    public void blankEmailShouldNotValidate() {
        Admin testAdmin;
        JSONObject adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("email", "");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), Admin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    public void blankPasswordShouldNotValidate() {
        Admin testAdmin;
        JSONObject adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), Admin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    public void passwordUnder8CharShouldNotValidate() {
        Admin testAdmin;
        JSONObject adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "1Admin@");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), Admin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    public void passwordWithoutUpperCaseCharShouldNotValidate() {
        Admin testAdmin;
        JSONObject adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "1admin@admin");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), Admin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    public void passwordWithoutLowerCharShouldNotValidate() {
        Admin testAdmin;
        JSONObject adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("password", "ADMIN@ADMIN");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), Admin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    public void blankQuestionShouldNotValidate() {
        Admin testAdmin;
        JSONObject adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("securityQuestion", "");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), Admin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }

    @Test
    public void blankAnswerShouldNotValidate() {
        Admin testAdmin;
        JSONObject adminJsonObject = initValidableJSONObject();
        adminJsonObject.replace("answerQuestion", "");

        try {
            testAdmin = mapper.readValue(adminJsonObject.toString(), Admin.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isSetWithViolationIsEmpty(validator, testAdmin));
    }
}
