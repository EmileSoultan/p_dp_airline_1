package app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "passport")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO думаю стоит создать свой валидатор, вот только непонятны критерии для проверки
    @NonNull
    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 1, max = 128, message = "Size serialNumberPassport cannot be less than 1 and more than 128 characters")
    @Column(name = "serial_number_passport",unique = true)
    private String serialNumberPassport;

    @NonNull
    @Column(name = "issuing_date", nullable = false)
    private LocalDate issuingDate;

    @NonNull
    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 1, max = 64, message = "Size issuingCountry cannot be less than 1 and more than 64 characters")
    @Column(name = "issuing_country")
    private String issuingCountry;

}
