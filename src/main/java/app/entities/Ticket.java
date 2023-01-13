package app.entities;

import app.entities.user.Passenger;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
@EqualsAndHashCode(of = {"ticketNumber"})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tickets")
    @SequenceGenerator(name = "seq_tickets", initialValue = 1000, allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull
    @Column(name = "ticket_number")
    private String ticketNumber;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    @JsonView
    private Passenger passenger;

    @NotNull
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "flight_id")
    @JsonView
    private Flight flight;

    @NotNull
    @OneToOne
    @JoinColumn(name = "flight_seat_id")
    @JsonView
    private FlightSeat flightSeat;
}
