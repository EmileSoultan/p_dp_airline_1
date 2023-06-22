package app.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NullPointerExceptionHandlerTest {

    @Test
    public void testHandleNullPointerException() {
        ValidationExceptionHandler handler = new ValidationExceptionHandler();
        String errorMessage = "Null pointer exception";
        ResponseEntity<ResponseExceptionDTO> response = handler.handleNullPointerException(new NullPointerException(errorMessage));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getExceptionMessage().contains(errorMessage));
    }
}
