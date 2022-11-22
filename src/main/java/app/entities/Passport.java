package app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passport {

    @Column(name = "serial_number_passport")
    @Pattern(regexp = "\\d{4}\\s\\d{6}")
    private String serialNumberPassport;

    @Column(name = "passport_issuing_date")
    private LocalDate passportIssuingDate;

    @Size(min = 1, max = 64, message = "Size issuingCountry cannot be less than 1 and more than 64 characters")
    @Column(name = "passport_issuing_country")
    private String passportIssuingCountry;

}
