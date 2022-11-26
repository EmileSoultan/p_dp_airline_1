package app.entities;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "flight_seats")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"flight", "seat"})
public class FlightSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flight_seats")
    @SequenceGenerator(name = "seq_flight_seats", initialValue = 1000, allocationSize = 1)
    Long id;

    @PositiveOrZero(message = "Fare must be positive")
    @Column(name = "fare")
    private Integer fare;

    @NotNull
    @Column(name = "is_registered")
    private Boolean isRegistered;

    @NotNull
    @Column(name = "is_sold")
    private Boolean isSold;

    @Column(name = "is_booking")
    private Boolean isBooking;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "flight_id")
    @JsonView
    Flight flight;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seat_id")
    @JsonView
    Seat seat;
}
