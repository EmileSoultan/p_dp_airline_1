package app.entities;

import app.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "passenger")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue
    private Long id;

    /*TODO может прописать свой валидатор для email? @Email заточен на локальную почту (а у нас её не будет)
     * и принимает значения без домена почтового сервиса, ему @ достаточно и всё
     */
    @NonNull
    @NotEmpty(message = "The field cannot be empty")
    @Email(message = "Incorrect email")
    @Size(min = 1, max = 256, message = "Size email cannot be less than 1 and more than 256 characters")
    @Column(unique = true)
    private String email;

    @NonNull
    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 1, max = 64, message = "Size phone cannot be less than 1 and more than 64 characters")
    @Column(name = "phone_number")
    private String phoneNubmer;

    @NonNull
    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 2, max = 128, message = "Size first_name cannot be less than 2 and more than 128 characters")
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 2, max = 128, message = "Size last_name cannot be less than 2 and more than 128 characters")
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 2, max = 128, message = "Size middle_name cannot be less than 2 and more than 128 characters")
    @Column(name = "middle_name")
    private String middleName;

    @NonNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @ApiModelProperty(dataType = "string",
            value = "Return values \"мужской\" or \"женский\", accepts values see example",
            example = "\"male\", \"MALE\", \"m\", \"M\", \"female\", \"FEMALE\", \"f\", \"F\"")
    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id")
    private Passport passport;

}
