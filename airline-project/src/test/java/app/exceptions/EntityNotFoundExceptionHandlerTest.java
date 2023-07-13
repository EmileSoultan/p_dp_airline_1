package app.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityNotFoundExceptionHandlerTest {

    @Test
    void entityNotFoundExceptionHandlerTest() {
        ValidationExceptionHandler validationExceptionHandler = new ValidationExceptionHandler();
        String errorMessage = "EntityNotFound exception";
        ResponseEntity<ResponseExceptionDTO> response = validationExceptionHandler
                .handleEntityNotFoundException(new EntityNotFoundException("EntityNotFound exception"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().getExceptionMessage().contains(errorMessage));
    }
}
