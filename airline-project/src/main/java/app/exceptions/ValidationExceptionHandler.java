package app.exceptions;

import org.postgresql.util.PSQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ResponseExceptionDTO> handleNullPointerException(NullPointerException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseExceptionDTO NullPointerExceptionDto = new ResponseExceptionDTO(errors.toString(), LocalDateTime.now());
        return new ResponseEntity<>(NullPointerExceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PSQLException.class})
    public ResponseEntity<ResponseExceptionDTO> handlePSQLException(PSQLException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseExceptionDTO PSQLExceptionDto = new ResponseExceptionDTO(errors.toString(), LocalDateTime.now());
        return new ResponseEntity<>(PSQLExceptionDto, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ResponseExceptionDTO> handleRuntimeException(RuntimeException exception) {
        ResponseExceptionDTO runtimeExceptionDTO = new ResponseExceptionDTO(exception.getMessage(), LocalDateTime.now());
        if (exception instanceof HttpMessageNotReadableException) {
            return new ResponseEntity<>(runtimeExceptionDTO, HttpStatus.BAD_REQUEST);
        }  else {
            return new ResponseEntity<>(runtimeExceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ResponseExceptionDTO> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        ResponseExceptionDTO constraintViolationExceptionDto = new ResponseExceptionDTO(errors.toString(), LocalDateTime.now());
        return new ResponseEntity<>(constraintViolationExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BindException.class})
    public ResponseEntity<List<ResponseExceptionDTO>> handleException(BindException exception) {
        List<ResponseExceptionDTO> validationExceptionDto = bindFieldsExceptionsToList(exception, new ArrayList<>());
        return new ResponseEntity<>(validationExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<ResponseExceptionDTO> handleException(SQLException exception) {
        ResponseExceptionDTO sqlExceptionDto = new ResponseExceptionDTO(exception.getMessage(), LocalDateTime.now());
        if (exception.getSQLState().equals("23505")) {
            return new ResponseEntity<>(sqlExceptionDto, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(sqlExceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ResponseExceptionDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseExceptionDTO EntityNotFoundExceptionDto = new ResponseExceptionDTO(errors.toString(), LocalDateTime.now());
        return new ResponseEntity<>(EntityNotFoundExceptionDto, HttpStatus.NOT_FOUND);
    }

    private List<ResponseExceptionDTO> bindFieldsExceptionsToList(
            BindException e,
            List<ResponseExceptionDTO> entityFieldsErrorList) {
        e.getFieldErrors().stream().forEach(a -> {
            entityFieldsErrorList.add(new ResponseExceptionDTO(a.getField() + " " + a.getDefaultMessage(), LocalDateTime.now()));
        });
        return entityFieldsErrorList;
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<ResponseExceptionDTO> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        ResponseExceptionDTO emptyResultDataAccessException = new ResponseExceptionDTO(errors.toString(), LocalDateTime.now());
        return new ResponseEntity<>(emptyResultDataAccessException, HttpStatus.NOT_FOUND);
    }

}