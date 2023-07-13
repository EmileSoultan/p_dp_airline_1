package app.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmptyResultDataAccessExceptionHandlerTest {

    @Test
    void emptyResultDataAccessExceptionHandlerTest() {
        ValidationExceptionHandler validationExceptionHandler = new ValidationExceptionHandler();
        String errorMessage = "EmptyResultDataAccess exception";
        ResponseEntity<ResponseExceptionDTO> response = validationExceptionHandler
                .handleEmptyResultDataAccessException(new EmptyResultDataAccessException("EmptyResultDataAccess exception",1));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().getExceptionMessage().contains(errorMessage));
    }
}

