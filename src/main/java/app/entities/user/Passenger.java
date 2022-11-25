package app.entities.user;

import app.entities.Passport;
import app.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "passengers")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"firstName", "lastName", "middleName", "passport"}, callSuper = true)
public class Passenger extends User {

    @Size(min = 2, max = 128, message = "Size first_name cannot be less than 2 and more than 128 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, max = 128, message = "Size last_name cannot be less than 2 and more than 128 characters")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Size(min = 1, max = 64, message = "Size phone cannot be less than 1 and more than 64 characters")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(min = 2, max = 128, message = "Size middle_name cannot be less than 2 and more than 128 characters")
    @Column(name = "middle_name")
    private String middleName;

    @ApiModelProperty(dataType = "string",
            value = "Return values \"male\" or \"female\", accepts values see example",
            example = "\"male\", \"MALE\", \"m\", \"M\", \"female\", \"FEMALE\", \"f\", \"F\"")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Passport passport;

}
