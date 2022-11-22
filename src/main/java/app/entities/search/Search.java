package app.entities.search;

import app.entities.Destination;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "searches")
@Setter
@Getter
@NoArgsConstructor
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Destination from;

    @ManyToOne
    private Destination to;

    @NotNull
    @Column(name = "departure_date", columnDefinition = "DATE", nullable = false)
    private LocalDate departureDate;

    @Column(name = "return_date", columnDefinition = "DATE")
    private LocalDate returnDate;

    @Positive
    @Column(name = "number_of_passengers", nullable = false)
    private Integer numberOfPassengers;

    public Search(Destination from, Destination to, LocalDate departureDate, LocalDate returnDate, Integer numberOfPassengers) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.numberOfPassengers = numberOfPassengers;
    }
}
