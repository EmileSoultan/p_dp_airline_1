package app.exceptions;

import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PSQLExceptionHandlerTest {

    @Test
    void PSQLExceptionHandlerTest() {
        ValidationExceptionHandler validationExceptionHandler = new ValidationExceptionHandler();
        String errorMessage = "PSQL exception";
        ResponseEntity<ResponseExceptionDTO> response = validationExceptionHandler
                .handlePSQLException(new PSQLException(errorMessage, null));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getExceptionMessage().contains(errorMessage));
    }


}
