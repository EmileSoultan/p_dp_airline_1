package app.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RuntimeExceptionHandlerTest {

    @Test
    void runtimeExceptionHandlerTest() {
        ValidationExceptionHandler validationExceptionHandler = new ValidationExceptionHandler();
        RuntimeException runtimeException = new RuntimeException();

        ResponseEntity<ResponseExceptionDTO> response = validationExceptionHandler.handleRuntimeException(runtimeException);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

