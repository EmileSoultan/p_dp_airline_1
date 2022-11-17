package app.entities.user;

import app.entities.Passport;
import app.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends User {

    /*TODO может прописать свой валидатор для email? @Email заточен на локальную почту (а у нас её не будет)
     * и принимает значения без домена почтового сервиса, ему @ достаточно и всё
     */

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
    @Column(name = "birth_date")
    private Date birthDate;

    @NonNull
//    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 1, max = 64, message = "Size phone cannot be less than 1 and more than 64 characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NonNull
//    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 2, max = 128, message = "Size middle_name cannot be less than 2 and more than 128 characters")
    @Column(name = "middle_name")
    private String middleName;

    @NonNull
//    @NotNull(message = "The field cannot be empty")
    @ApiModelProperty(dataType = "string",
            value = "Return values \"мужской\" or \"женский\", accepts values see example",
            example = "\"male\", \"MALE\", \"m\", \"M\", \"female\", \"FEMALE\", \"f\", \"F\"")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    private Passport passport;

}
