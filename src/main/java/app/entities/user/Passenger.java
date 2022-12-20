package app.entities.user;

import app.entities.Passport;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName", "birthDate", "phoneNumber", "passport"}, callSuper = true)
public class Passenger extends User {

    @NotBlank(message = "Field should not be empty")
    @Size(min = 2, max = 128, message = "Size first_name cannot be less than 2 and more than 128 characters")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Field should not be empty")
    @Size(min = 2, max = 128, message = "Size last_name cannot be less than 2 and more than 128 characters")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "Field should not be empty")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth can not be a future time")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotBlank(message = "Field should not be empty")
    @Size(min = 6, max = 64, message = "Size phone cannot be less than 6 and more than 64 characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Valid
    @Embedded
    private Passport passport;
}
