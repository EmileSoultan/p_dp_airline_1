package app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidationExceptionDto {
    private String notValidField;
    private Object notValidValue;
    private String validCondition;
}