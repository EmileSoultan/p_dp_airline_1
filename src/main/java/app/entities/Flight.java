package app.entities;

import app.enums.FlightStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights",
        indexes = {
                @Index(columnList = "departure_date", name = "departure_index")
        })
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"code"})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_flights")
    @SequenceGenerator(name = "seq_flights", initialValue = 1000, allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Code cannot be empty")
    @Column(name = "code")
    @Size(min = 2, max = 15, message = "Length of Flight code should be between 2 and 15 characters")
    private String code;

    @ManyToOne
    private Destination from;

    @ManyToOne
    private Destination to;

    @Column(name = "departure_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime departureDateTime;

    @Column(name = "arrival_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime arrivalDateTime;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    @JsonView
    private Aircraft aircraft;

    @Enumerated(EnumType.STRING)
    @Column(name = "flight_status")
    private FlightStatus flightStatus;
}
