package app.exceptions;

import app.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ErrorDto>> handleException(BindException exception) {
        List<ErrorDto> errorDto = convertToErrorDtoList(exception);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    private List<ErrorDto> convertToErrorDtoList(BindException e) {
        List<ErrorDto> resultErrorDtoList = new ArrayList<>();
        for (int i = 0; i < e.getFieldErrors().size(); i++) {
            resultErrorDtoList.add(new ErrorDto(
                    e.getFieldErrors().get(i).getField(),
                    e.getFieldErrors().get(i).getRejectedValue(),
                    e.getFieldErrors().get(i).getDefaultMessage()));

        }
        return resultErrorDtoList;
    }
}