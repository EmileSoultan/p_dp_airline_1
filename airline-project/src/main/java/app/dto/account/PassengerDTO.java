package app.dto.account;


import app.entities.Passport;
import app.entities.account.Passenger;
import app.entities.account.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonTypeName(value = "passenger")
public class PassengerDTO {


    @NotBlank(message = "Field should not be empty")
    @Size(min = 2, max = 128, message = "Size first_name cannot be less than 2 and more than 128 characters")
    private String firstName;

    @NotBlank(message = "Field should not be empty")
    @Size(min = 2, max = 128, message = "Size last_name cannot be less than 2 and more than 128 characters")
    private String lastName;

    @NotNull(message = "Field should not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth can not be a future time")
    private LocalDate birthDate;

    @NotBlank(message = "Field should not be empty")
    @Size(min = 6, max = 64, message = "Size phone cannot be less than 6 and more than 64 characters")
    private String phoneNumber;

    private Passport passport;



    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Email
    @NotBlank(message = "The field cannot be empty")
    private String email;

    @NotBlank(message = "The field cannot be empty")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,}", message = "min 8 characters, 1 uppercase latter" +
            "1 lowercase latter, at least 1 number, 1 special character")
    private String password;

    @NotBlank(message = "The field cannot be empty")
    private String securityQuestion;

    @NotBlank(message = "The field cannot be empty")
    private String answerQuestion;

    private Set<Role> roles;


    public PassengerDTO(Passenger passenger) {
        this.id = passenger.getId();
        this.firstName = passenger.getFirstName();
        this.lastName = passenger.getLastName();
        this.birthDate = passenger.getBirthDate();
        this.phoneNumber = passenger.getPhoneNumber();
        this.passport = passenger.getPassport();
        this.email = passenger.getEmail();
        this.answerQuestion = passenger.getAnswerQuestion();
        this.securityQuestion = passenger.getSecurityQuestion();
        this.password = passenger.getPassword();
        this.roles = passenger.getRoles();
    }
}
