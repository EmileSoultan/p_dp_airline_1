package app.exceptions;

import app.dto.SqlExceptionDto;
import app.dto.ValidationExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ValidationExceptionDto>> handleException(BindException exception) {
        List<ValidationExceptionDto> validationExceptionDto = convertToErrorDtoList(exception);
        return new ResponseEntity<>(validationExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<SqlExceptionDto> handleException(SQLException exception) {
            SqlExceptionDto sqlExceptionDto = new SqlExceptionDto(exception.getMessage(), LocalDateTime.now());
            return new ResponseEntity<>(sqlExceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<ValidationExceptionDto> convertToErrorDtoList(BindException e) {
        List<ValidationExceptionDto> resultValidationExceptionDtoList = new ArrayList<>();
        for (int i = 0; i < e.getFieldErrors().size(); i++) {
            resultValidationExceptionDtoList.add(new ValidationExceptionDto(
                    e.getFieldErrors().get(i).getField(),
                    e.getFieldErrors().get(i).getRejectedValue(),
                    e.getFieldErrors().get(i).getDefaultMessage()));

        }
        return resultValidationExceptionDtoList;
    }
}