package app.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_from_id", referencedColumnName = "id")
    @NotEmpty
    private Destination destinationFrom;

    @ManyToOne
    @JoinColumn(name = "destination_to_id", referencedColumnName = "id")
    @NotEmpty
    private Destination destinationTo;

    @NotEmpty
    @Column(name = "departure_date")
    private LocalDate departureDate;

    @NotEmpty
    @Column(name = "number_of_passengers")
    private Integer numberOfPassengers;

//    @ManyToOne
//    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    private Category category;

    public Route(Destination destinationFrom, Destination destinationTo, LocalDate departureDate, Integer numberOfPassengers) {
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.departureDate = departureDate;
        this.numberOfPassengers = numberOfPassengers;
    }
}
