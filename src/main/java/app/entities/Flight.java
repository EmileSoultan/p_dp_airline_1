package app.entities;

import app.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "flight")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @ManyToOne
    private Destination from;

    @ManyToOne
    private Destination to;

    @Column(name = "departure_date")
    private LocalDateTime departureDateTime;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDateTime;

    @OneToOne
    private Aircraft aircraft;

    @Enumerated(EnumType.STRING)
    private FlightStatus flightStatus;
}
